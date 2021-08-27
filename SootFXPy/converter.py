import pandas as pd


def to_dataframe(feature_set):
    my_set = []
    for feature in feature_set:
        instance = {"name": feature.getSignature()}
        for f in feature.getFeatures():
            instance[f.getName()] = f.getValue()
        my_set.append(instance)
    df = pd.DataFrame(my_set)
    return df
