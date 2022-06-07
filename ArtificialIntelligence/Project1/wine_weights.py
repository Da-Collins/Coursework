import sys
import csv
import statistics
import numpy as np


def read_data(csv_path):
    """Read in the input data from csv.
    
    Args:
        csv_path: the path to the input file.  The data file is assumed to have a header row, and
                the class value is found in the last column.
        standardize: flag to indicate whether the attributes (not including the class label) should 
                be transformed into z-scores before returning

    Returns: a list of lists containing the labeled training examples, with each row representing a 
        single example of the format [ attr1, attr2, ... , class_label ]
    """
    with open(csv_path, 'r') as csvfile:
        reader = csv.reader(csvfile)
        next(reader)  # header row
        features = []
        labels = []
        for row in reader:
            features.append([ float(r) for r in row[:-1] ])            
            labels.append(row[-1])
        examples = [ row + [labels[i]] for i, row in enumerate(features) ]
        return examples


def standardize(examples):
    """Transform data to use z-scores instead of raw values.  
        
    Args:
        examples: a list of lists containing the training examples, with each row representing a 
            single example of the format [ attr1, attr2, ... , class_label ]
    
    Returns: a list of lists containing the transformed training examples, with each row 
        representing a single example of the format [ zscore1, zscore2, ... , class_label ]

    See: https://en.wikipedia.org/wiki/Standard_score for more detail on z-scores.  N.B.: the last
        field each row is assumed to contain the class label and is not transformed!    
    """
    zscores = []
    means = np.array(examples[0][0:len(examples[0])-1]).astype(np.float)
    examp = examples[1:len(examples)]
    for i in examp:
        i = np.array(i[0:len(i)-1])
        means = np.add(i, means)
    means = np.divide(means, len(examples))
    sds = np.array(examples[0][0:len(examples[0])-1]).astype(np.float)
    for i in examp:
        i = i[0:len(i)-1]
        sds = np.add(sds, np.absolute(np.power(np.subtract(i, means), 2)))
    sds = np.divide(means, len(examples))
    j = 0
    for i in examples:
        i = i[0:len(i) - 1]
        zscores.append(np.divide(np.subtract(i, means), sds))
        np.append(zscores[j], examples[j][len(i)])
        j = j + 1

    return zscores  # Fix this line!

def learn_weights(examples):
    """Learn attribute weights for a multiclass perceptron.

    Args:
        examples: a list of lists containing the training examples, with each row representing a 
            single example of the format [ attr1, attr2, ... , class_label ]
                  
    Returns: a dictionary containing the weights for each attribute, for each class, that correctly
            classify the training data.  The keys of the dictionary should be the class values, and
            the values should be lists attribute weights in the order they appear in the data file.
            For example, if there are four attributes and three classes (1-3), the output might take
            the form:
                { 1 => [0.1, 0.8, 0.5, 0.01],
                  2 => [0.9, 0.01, 0.05, 0.4],
                  3 => [0.01, 0.2, 0.3, 0.85] }
    """
    weights = {}  # one set of weights for each class

    for i in range(1000):
        right = True
        hi = 0
        for j in examples:
            if not j[len(j) - 1] in weights:
                weights.update({str(j[len(j) - 1]):np.array(j[0:len(j) -1])})
                continue
            correct = j[len(j) - 1]
            j = np.array(j[0:len(j) - 1])
            generated = -1
            maxWeight = -1
            for k in weights:
                value = j.dot(weights.get(k))
                if value > maxWeight:
                    maxWeight = value
                    generated = k
            if generated != correct:
                hi = hi + 1
                i = i + 1
                right = False
                weights[correct] = np.add(weights[correct], j)
                weights[generated] = np.subtract(weights[generated], j)
        if right:
            break
    return weights


def print_weights(class__weights):
    for c, wts in sorted(class__weights.items()):
        print("class {}: {}".format(c, ",".join([str(w) for w in wts])))




#############################################

if __name__ == '__main__':

    path_to_csv = "wine.csv"
    training_data = read_data(path_to_csv)

    class__weights = learn_weights(training_data)
    print_weights(class__weights)

    training_data = standardize(training_data)
    class__weights = learn_weights(training_data)
    print_weights(class__weights)







