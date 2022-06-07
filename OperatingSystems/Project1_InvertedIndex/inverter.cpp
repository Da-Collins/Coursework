#include <map>
#include <queue>
#include <set>
#include <string>

#include <inverter.h>

using namespace std;

string build_inverted_index(string filename) {
  string returned;
  map<string, set<int> > counter;
  ifstream file;
  file.open(filename);
  string text;
  int i = 0;
  while(getline(file, text)){
    ifstream textFile;
    textFile.open(text);
    string line;
    while(getline(textFile, line)){
      string word = "";
      for(auto c: line){
	if(isalpha(c)){
          word += c;
        }
        else if(word != ""){
          counter[word].insert(i);
          word = "";
	}
      }
      if(word!=""){
        counter[word].insert(i);
      }
    }
    i++;
  }
  for(auto pair: counter){
    returned += pair.first + ":";
    for(auto doc: pair.second){
      returned += " " + to_string(doc); 
    }
    returned+= "\n";
  }
  return returned;
}
