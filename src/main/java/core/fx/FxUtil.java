package core.fx;

import api.FeatureDescription;
import api.FeatureGroup;
import core.fx.base.ClassFeatureExtractor;
import core.fx.base.ManifestFeatureExtractor;
import core.fx.base.MethodFeatureExtractor;
import core.fx.base.WholeProgramFeatureExtractor;
import org.reflections.Reflections;
import soot.*;
import soot.jimple.infoflow.android.axml.AXmlAttribute;
import soot.jimple.infoflow.android.axml.AXmlNode;
import soot.jimple.infoflow.android.manifest.ProcessManifest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FxUtil {

    private static Reflections reflections = new Reflections("core.fx");

    public static boolean isOfType(soot.Type type, String typeName) {
        if (!(type instanceof RefType)) return false;

        // Check for a direct match
        RefType refType = (RefType) type;
        if (refType.getSootClass().getName().equals(typeName)) return true;

        // interface treatment
        if (refType.getSootClass().isInterface()) return false;

        // class treatment
        Hierarchy h = Scene.v().getActiveHierarchy();
        List<SootClass> ancestors = h.getSuperclassesOf(refType.getSootClass());
        for (SootClass ancestor : ancestors) {
            if (ancestor.getName().equals(typeName)) return true;
            for (SootClass sc : ancestor.getInterfaces())
                if (sc.getName().equals(typeName)) return true;
        }
        return false;
    }

    public static List<String> getManifestUsesFeature(ProcessManifest manifest) {
        List<String> usesFeatures = new ArrayList<>();
        List<AXmlNode> usesSdk = manifest.getManifest().getChildrenWithTag("uses-sdk");
        if (usesSdk != null && !usesSdk.isEmpty()) {
            for (AXmlNode aXmlNode : usesSdk) {
                AXmlAttribute<?> nameAttr = aXmlNode.getAttribute("name");
                if(nameAttr!=null){
                    String name = (String) nameAttr.getValue();
                    usesFeatures.add(name);
                }
            }
        }
        return usesFeatures;
    }

    public static boolean isAppMethod(SootMethod m){
        String pkg = m.getDeclaringClass().getPackageName();
        return isAppPackage(pkg);
    }

    public static boolean isAppClass(SootClass sc){
        String pkg = sc.getPackageName();
        return isAppPackage(pkg);
    }

    public static boolean isAppPackage(String pkg){
        return !pkg.startsWith("android.") && !pkg.startsWith("java.") && !pkg.startsWith("javax.") && !pkg.startsWith("dalvik.") && !pkg.startsWith("junit.") &&
                !pkg.startsWith("org.apache") && !pkg.startsWith("org.json") && !pkg.startsWith("org.w3c") && !pkg.startsWith("org.xml")
                && !pkg.startsWith("org.ietf.") && !pkg.startsWith("org.omg.") && !pkg.startsWith("sun.") && !pkg.startsWith("jdk.");
    }

    public static List<FeatureDescription> listAllMethodFeatures(){
        List<FeatureDescription> methodList = new ArrayList<>();
        Set<Class<? extends MethodFeatureExtractor>> methodBased = reflections.getSubTypesOf(MethodFeatureExtractor.class);
        for (Class<? extends MethodFeatureExtractor> m : methodBased) {
            FeatureDescription desc = new FeatureDescription(m.getSimpleName(), ""); // TODO: define descriptions and get
            methodList.add(desc);
        }
        return methodList;
    }

    public static List<FeatureDescription> listAllClassFeatures(){
        Set<Class<? extends ClassFeatureExtractor>> classBased = reflections.getSubTypesOf(ClassFeatureExtractor.class);
        List<FeatureDescription> classList = new ArrayList<>();
        for (Class<? extends ClassFeatureExtractor> m : classBased) {
            FeatureDescription desc = new FeatureDescription(m.getSimpleName(), ""); // TODO: define descriptions and get
            classList.add(desc);
        }
        return classList;
    }

    public static List<FeatureDescription> listAllWholeProgramFeatures(){
        Set<Class<? extends WholeProgramFeatureExtractor>> wholeProgramBased = reflections.getSubTypesOf(WholeProgramFeatureExtractor.class);
        List<FeatureDescription> wpList = new ArrayList<>();
        for (Class<? extends WholeProgramFeatureExtractor> m : wholeProgramBased) {
            FeatureDescription desc = new FeatureDescription(m.getSimpleName(), ""); // TODO: define descriptions and get
            wpList.add(desc);
        }
        return wpList;
    }

    public static List<FeatureDescription> listAllManifestFeatures(){
        Set<Class<? extends ManifestFeatureExtractor>> manifestBased = reflections.getSubTypesOf(ManifestFeatureExtractor.class);
        List<FeatureDescription> manifestList = new ArrayList<>();
        for (Class<? extends ManifestFeatureExtractor> m : manifestBased) {
            FeatureDescription desc = new FeatureDescription(m.getSimpleName(), ""); // TODO: define descriptions and get
            manifestList.add(desc);
        }
        return manifestList;
    }

    public static List<FeatureGroup> listAllFeatures(){
        List<FeatureGroup> allFeatures = new ArrayList<>();

        List<FeatureDescription> methodList = listAllMethodFeatures();
        FeatureGroup methodGroup = new FeatureGroup("methodbased", methodList);
        allFeatures.add(methodGroup);

        List<FeatureDescription> classList = listAllClassFeatures();
        FeatureGroup classGroup = new FeatureGroup("classbased", classList);
        allFeatures.add(classGroup);

        List<FeatureDescription> wpList = listAllWholeProgramFeatures();
        FeatureGroup wpGroup = new FeatureGroup("wholeprogrambased", wpList);
        allFeatures.add(wpGroup);

        List<FeatureDescription> manifestList = listAllManifestFeatures();
        FeatureGroup manifestGroup = new FeatureGroup("wholeprogrambased", manifestList);
        allFeatures.add(manifestGroup);

        return allFeatures;
    }

}
