#include <scheduling.h>
#include <fstream>
#include <iostream>
#include <list>
#include <queue>
#include <string>
#include <sstream>

using namespace std;

pqueue_arrival read_workload(string filename) {
  ifstream file;
  file.open(filename);
  string line;
  pqueue_arrival workload;
  while(getline(file, line)){
    Process p;
    std::istringstream is(line);
    int arrival, duration;
    is >> arrival >> duration;
    p.arrival = arrival;
    p.duration = duration;
    workload.push(p);
  }
  return workload;
}

void show_workload(pqueue_arrival workload) {
  pqueue_arrival xs = workload;
  cout << "Workload:" << endl;
  while (!xs.empty()) {
    Process p = xs.top();
    cout << '\t' << p.arrival << ' ' << p.duration << endl;
    xs.pop();
  }
}

void show_processes(list<Process> processes) {
  list<Process> xs = processes;
  cout << "Processes:" << endl;
  while (!xs.empty()) {
    Process p = xs.front();
    cout << "\tarrival=" << p.arrival << ", duration=" << p.duration
         << ", first_run=" << p.first_run << ", completion=" << p.completion
         << endl;
    xs.pop_front();
  }
}

list<Process> fifo(pqueue_arrival workload) {
  pqueue_arrival xs = workload;
  list<Process> complete;
  int time = 0;
  while(!xs.empty()){
      Process p = xs.top();
      if(p.arrival > time){
        time = p.arrival;
      }
      p.first_run = time;
      p.completion = p.duration + time;
      time = p.duration + time;
      complete.push_back(p);
      xs.pop();
  }
  return complete;
}

list<Process> sjf(pqueue_arrival workload) {
  pqueue_arrival xs = workload;
  pqueue_duration dur;
  list<Process> complete;
  int time = 0;
  while(!xs.empty() || !dur.empty()){
    while(xs.top().arrival <= time && !xs.empty()){
      dur.push(xs.top());
      xs.pop();
    }
    if(dur.empty()){
      time++;
      continue;
    }
    Process p = dur.top();
    p.first_run = time;
    p.completion = p.duration + time;
    time = p.duration + time;
    complete.push_back(p);
    dur.pop();
  }
  return complete;
}

list<Process> stcf(pqueue_arrival workload) {
  pqueue_arrival xs = workload;
  pqueue_duration dur;
  list<Process> complete;
  int time = 0;
  while(!xs.empty() || !dur.empty()){
    while(xs.top().arrival <= time  && !xs.empty()){
      Process x = xs.top();
      x.first_run = -1;
      dur.push(x);
      xs.pop();
    }
    if(dur.empty()){
      time++;
      continue;
    }
    Process p = dur.top();
    dur.pop();
    if(p.first_run == -1){
      p.first_run = time;
    }
    p.duration--;
    time++;
    if(p.duration == 0){
      p.completion = time;
      complete.push_back(p);
    }
    else{
      dur.push(p);
    }
  }
  return complete;
}

list<Process> rr(pqueue_arrival workload) {
  pqueue_arrival xs = workload;
  list<Process> complete;
  list<Process> roundrobin;
  int time = 0;
  while(!xs.empty() || !(roundrobin.size() == 0)){
    while(xs.top().arrival <= time  && !xs.empty()){
      Process x = xs.top();
      x.first_run = -1;
      roundrobin.push_back(x);
      xs.pop();
    }
    if(roundrobin.size() == 0){
      time++;
      continue;
    }
    Process p = roundrobin.front();
    roundrobin.pop_front();
    if(p.first_run == -1){
      p.first_run = time;
    }
    p.duration--;
    time++;
    if(p.duration == 0){
      p.completion = time;
      complete.push_back(p);
    }
    else{
      roundrobin.push_back(p);
    }
  }
  return complete;
}

float avg_turnaround(list<Process> processes) {
  float average = 0;
  for(list<Process>::iterator p = processes.begin(); p != processes.end(); ++p){
    average += p->completion - p->arrival;
  }
  average = average/processes.size();
  return average;
}

float avg_response(list<Process> processes) {
  float average = 0;
  for(list<Process>::iterator p = processes.begin(); p != processes.end(); ++p){
    average += p->first_run - p->arrival;
  }
  average = average/processes.size();
  return average;
}

void show_metrics(list<Process> processes) {
  float avg_t = avg_turnaround(processes);
  float avg_r = avg_response(processes);
  show_processes(processes);
  cout << '\n';
  cout << "Average Turnaround Time: " << avg_t << endl;
  cout << "Average Response Time:   " << avg_r << endl;
}
