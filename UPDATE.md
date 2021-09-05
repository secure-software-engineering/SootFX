# Update
Below we address the issues:
1. There is no soot-infoflow-android in maven central:
    - This library is not available in maven central, therefore we now include it in `dependencies` folder. It can be installed to the local maven repository by running the `install_dependencies.sh` script.

2. Pom is not configured to generate a standalone jar:
    - we now use the maven assembly plugin, a jar with dependencies can be built with `mvn package` this will create `SootFX-1.0-SNAPSHOT-jar-with-dependencies.jar` in `target` folder.

3. Missing python requirements:
    - Python requirements can be installed with `pip install -r requirements.txt`

4. Fixed python implementation issues. (Wrong method name)

5. Fixed NullPointerException when methodFeatureInclusion/methodFeatureExclusion is not specified in config.yaml

6. NullPointerException when both Inclusion and Exclusion lists are given:
    - Only one of the lists must be provided. Now we throw an appropriate exception when both are set.

7. Fixed NullPointerException when file.getParentFile() returns null in api.SootFX.printMultiSetToCSV.

8. SootFX wasn't tested with other java version, therefore we recommend using Java 8.