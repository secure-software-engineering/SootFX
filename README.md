# SootFX
SootFX is a static code feature extraction tool for Java and Android, built to be extended with new resource providers and feature extraction units. It currently extracts method, class and whole-program features for jar and apk files, for apks it also extracts manifest features. 

# Building
SootFX is a java project and uses maven. It wasn't tested on other Java versions therefore we recommend using Java version 8. It can be imported as a maven project to your favourite IDE and can be built with maven plugins.
It depends on soot-infoflow-android which is not available on maven central, this library can be installed to the local maven repository by running the `install_dependencies.sh` script in `dependencies` folder.
After that it can be built with:

```
mvn install
```

# Usage
SootFX can be used with its Java API, Python API or CLI.
## Java API
The Java API can be accessed via [api.SootFX](src/main/java/api/SootFX.java)
An example for extracting all the method features:

```java
SootFX sootFX = new SootFX();
sootFX.addClassPath(path); //path to jar or apk file
Set<MethodFeatureSet> featureSets = sootFX.extractAllMethodFeatures();
sootFX.printMultiSetToCSV(featureSets, outPath); //path to output csv file
```
## Python API
Python requirements are defined in [requirements.txt](SootFXPy/requirements.txt), and can be installed by running:

```
pip install -r requirements.txt
```

The Python API can be accessed in two steps:
1. run [api.SootFXEntryPoint](src/main/java/api/SootFXEntryPoint.java). It starts a Py4J gateway server.
2. run [main.py in SootFXPy](SootFXPy/main.py), which enables accessing the Java API over the gateway

Obtaining the API handle:
```python
gateway = JavaGateway()
sootFX = gateway.entry_point.sootFX()
```
Listing all the method features:
```python
availableFeatures = sootFX.listAllMethodFeatures()
```

Extracting selected method features as Pandas DataFrame:
```python
selected_features = gateway.jvm.java.util.ArrayList()
selected_features.add('MethodAssignStmtCount')
selected_features.add('MethodBranchCount')
extracted_features = sootFX.extractMethodFeatures(selected_features)
df = converter.to_dataframe(extracted_features)
```

## CLI
An executable jar can be built with:

```
mvn package
```

This will create `SootFX-1.0-SNAPSHOT-jar-with-dependencies.jar` under `target` folder. it can be used as a CLI tool.  
in case of jar files:
- to extract all the features:
    ```
    java -jar SootFX.jar "path/to/jar" "path/to/out/"
    ```
- to extract features by using inclusion or exclusion lists:
    ```
    java -jar SootFX.jar "path/to/jar" "path/to/out/" "path/to/config.yaml"
    ```
in case of apk files:
- to extract all the features:
    ```
    java -jar SootFX.jar "path/to/apk" "path/to/out/" "path/to/Android/sdk/platforms"
    ```
- to extract features by using inclusion or exclusion lists:
    ```
    java -jar SootFX.jar "path/to/apk" "path/to/out/" "path/to/config.yaml" "path/to/Android/sdk/platforms"
    ```

inclusion and exclusion lists for different type of feature extraction units can be defined in [config.yaml](config.yaml). Make sure to provide either an inclusion list, or an exclusion list. Inclusion list only extracts the selected features. Exclusion list extracts all but the selected features.


## About

SootFX is developed by the [Secure Software Engineering Group of the Paderborn University](https://www.hni.uni-paderborn.de/en/sse/)

Feel free to report [issues](https://github.com/secure-software-engineering/SootFX/issues) if you find a bug or would like to implement a new feature.

## Publications
For further information, have a look at the publication [preprint](./paper_preprint.pdf).

If you use SootFX in your research projects, feel free to cite our paper:

```
@INPROCEEDINGS{9610670,
  author={Karakaya, Kadiray and Bodden, Eric},
  booktitle={2021 IEEE 21st International Working Conference on Source Code Analysis and Manipulation (SCAM)},   
  title={SootFX: A Static Code Feature Extraction Tool for Java and Android},   
  year={2021},  
  volume={},  
  number={},  
  pages={181-186},  
  doi={10.1109/SCAM52516.2021.00030}
 }
```
