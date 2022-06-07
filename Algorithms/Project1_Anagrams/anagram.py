import time
# Do not change the name of this file or the class name.
class Anagram(object):

    def __init__(self, filename):
        self.load_dictionary(filename)

    """
    Helper method to load the dictionary file.
    You may read it in some other way if you want to but do not change the function signature.
    """
    def load_dictionary(self, filename):
        with open(filename) as handle:
            self.dictionary = set(w.strip() for w in handle)

    #an implementation of radix sort using bucket sort as its stable sorting algorithm
    def radixSort(self, word):
        place = 1
        #no ascii values are in the 1000s
        while place < 1000:
            #create 10 empty buckets to store values
            bucket = [list() for _ in range(10)]
            for num in word:
                #calculate value at current place and place value in correct bucket
                index = num // place
                bucket[index%10].append(num)
            i = 0
            #go through each bucket and place contents in word so that it is now sorted by the digit at place
            for j in range(10):
                buckets = bucket[j]
                for k in buckets:
                    word[i] = k
                    i = i+1
            #go to next place
            place = place*10

    #function to convert array of asciis values to a radix-128 number
    def hash(self, word, m):
        i = 0
        hashValue = 0
        while i < len(word):
            hashValue = (hashValue * 128 + word[i])%m
            i = i+1
        return hashValue

    #checks to see if two words have the same characters
    def equals(self, word1, word2):
        for c in word1:
            if c not in word2:
                return False
        for c in word2:
            if c not in word1:
                return False
        return True

    """   
   * Implement the algorithm here. Do not change the function signature.
   *
   * @returns - List of lists, where each element of the outer list is a list containing all the words in that anagram class.
   * example: [['pots', 'stop', 'tops'], ['brake', 'break']]
    """
    def getAnagrams(self):
        #I expect at most 700k ish keys (size of Dict2) so this is a prime larger than that far from a power of 2
        m = 8124863
        #initialize array to be empty
        anagrams = [None] * m
        while self.dictionary:
            #get word and convert to array of its ascii values
            word = self.dictionary.pop()
            asciis = [ord(c) for c in word]
            #sort the array of asciis and compute the hash
            self.radixSort(asciis)
            hashValue = self.hash(asciis, m)
            #if there is no collision create new array containing word and place in hash table
            if anagrams[hashValue] is None:
                anagrams[hashValue] = [word]
            #deal with collision
            else:
                while 1:
                    #if we reach end of array loop around
                    if hashValue >= m:
                        hashValue = hashValue - m
                    #if we find a empty spot in hash table input word and exit loop
                    if anagrams[hashValue] is None:
                        anagrams[hashValue] = [word]
                        break
                    #if spot in hash table is not empty see if word is actually an anagram
                    elif self.equals(anagrams[hashValue][0], word):
                        anagrams[hashValue].append(word)
                        break
                    #linear probing
                    hashValue = hashValue + 1
        #filter out the none values from anagrams
        anagrams = filter(None, anagrams)
        return list(anagrams)

"""
You can use this for debugging if you wish.
"""
if __name__ == "__main__":
    pf = Anagram("Dict1.txt")
    pf.getAnagrams()

