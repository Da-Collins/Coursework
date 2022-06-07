#include <tsh.h>
#include <iostream>

using namespace std;

void simple_shell::parse_command(char* cmd, char** cmdTokens) {
    char* token;
    int i = 0;
    token = strtok(cmd, " \n");
    while(token != NULL){
	cmdTokens[i] = token;
        token = strtok(NULL, " \n");
	i++;
    }
    cmdTokens[i] = NULL;
}

void simple_shell::exec_command(char** argv) {
    int pid = fork();
    if(pid == 0){
	if(execvp(argv[0], argv)< 0){
	  exit(0);
        }
    }
    else{
        waitpid(pid, NULL, 0);
    }
}

bool simple_shell::isQuit(char* cmd) {
    if(strcmp(cmd, "quit")==0){
	return true;
    }
    return false;
}
