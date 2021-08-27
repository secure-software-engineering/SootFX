package core.fx;

import api.FeatureDescription;
import api.FeatureGroup;
import api.SootFX;
import core.fx.base.ClassFEU;
import core.fx.base.ManifestFEU;
import core.fx.base.MethodFEU;
import core.fx.base.WholeProgramFEU;
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
        if(SootFX.isAPK){
            return isAppPackage(pkg);
        }
        return isLibPackage(pkg);
    }

    public static boolean isAppClass(SootClass sc){
        String className = sc.getName();
        if(SootFX.isAPK){
            return isAppPackage(className);
        }
        return isLibPackage(className);
    }

    public static boolean isAppPackage(String name){
        return !name.startsWith("android.") && !name.startsWith("java.") && !name.startsWith("javax.") && !name.startsWith("dalvik.") && !name.startsWith("junit.") &&
                !name.startsWith("org.apache") && !name.startsWith("org.json") && !name.startsWith("org.w3c") && !name.startsWith("org.xml")
                && !name.startsWith("org.ietf.") && !name.startsWith("org.omg.") && !name.startsWith("sun.") && !name.startsWith("jdk.");
    }

    public static boolean isLibPackage(String name){
        return !name.startsWith("android.") && !name.startsWith("java.") && !name.startsWith("javax.") && !name.startsWith("dalvik.") && !name.startsWith("sun.") && !name.startsWith("jdk.");
    }

    public static List<FeatureDescription> listAllMethodFeatures(){
        List<FeatureDescription> methodList = new ArrayList<>();
        Set<Class<? extends MethodFEU>> methodBased = reflections.getSubTypesOf(MethodFEU.class);
        for (Class<? extends MethodFEU> m : methodBased) {
            FeatureDescription desc = new FeatureDescription(m.getSimpleName(), ""); // TODO: define descriptions and get
            methodList.add(desc);
        }
        return methodList;
    }

    public static List<FeatureDescription> listAllClassFeatures(){
        Set<Class<? extends ClassFEU>> classBased = reflections.getSubTypesOf(ClassFEU.class);
        List<FeatureDescription> classList = new ArrayList<>();
        for (Class<? extends ClassFEU> m : classBased) {
            FeatureDescription desc = new FeatureDescription(m.getSimpleName(), ""); // TODO: define descriptions and get
            classList.add(desc);
        }
        return classList;
    }

    public static List<FeatureDescription> listAllWholeProgramFeatures(){
        Set<Class<? extends WholeProgramFEU>> wholeProgramBased = reflections.getSubTypesOf(WholeProgramFEU.class);
        List<FeatureDescription> wpList = new ArrayList<>();
        for (Class<? extends WholeProgramFEU> m : wholeProgramBased) {
            FeatureDescription desc = new FeatureDescription(m.getSimpleName(), ""); // TODO: define descriptions and get
            wpList.add(desc);
        }
        return wpList;
    }

    public static List<FeatureDescription> listAllManifestFeatures(){
        Set<Class<? extends ManifestFEU>> manifestBased = reflections.getSubTypesOf(ManifestFEU.class);
        List<FeatureDescription> manifestList = new ArrayList<>();
        for (Class<? extends ManifestFEU> m : manifestBased) {
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
