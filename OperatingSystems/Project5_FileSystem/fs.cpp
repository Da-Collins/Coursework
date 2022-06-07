#include "fs.h"
#include <iostream>

using namespace std;

myFileSystem::myFileSystem(char diskName[16]) {
  // open the file with the above name
  // this file will act as the "disk" for your file system
  disk.open(diskName, fstream::in | fstream::out | fstream::binary);
}

int myFileSystem::create_file(char name[8], int size) {
  if(!disk.is_open()){
    return -1;
  }
  if(size > 8){
    return -1;
  }
  disk.seekg(0, disk.beg);
  char* freeList = new char[128];
  if(!disk.read(freeList, 128)){
    return -1;
  }
  int count = 0;
  for(int i = 0; i < 128; i++){
    if(freeList[i] == 0){
      count += 1;
    }
    if(count == size){break;}
  }
  if(count < size){
    return -1;
  }
  
  char* buf = new char[sizeof(idxNode)];
  disk.read(buf, sizeof(idxNode));
  idxNode * node = (idxNode *) buf;
  int nodeLoc = -1;
  for(int i = 0; i < 16; i++){
    if((*node).used != 1){
      nodeLoc = i;
      (*node).used = 1;
      strcpy((*node).name, name);
      (*node).size = size;
      break;
    }
    disk.read(buf, sizeof(idxNode));
    node = (idxNode *) buf;
  }
  if(nodeLoc == -1){
    return -1;
  }

  for(int i = 0; i < size; i++){
    for(int j = 0; j < 128; j++){
      if(freeList[j] == 0){
        freeList[j] = 1;
        (*node).blockPointers[i] = j;
        break;
      }
    }
  }

  disk.seekg(0, disk.beg);
  disk.write(freeList, 128);
  disk.seekg(128 + (sizeof(idxNode)*nodeLoc), disk.beg);
  disk.write((char*)node, sizeof(idxNode));

  // create a file with this name and this size
  // Step 1: Check to see if we have sufficient free space on disk by
  //   reading in the free block list. To do this:
  // Move the file pointer to the start of the disk file.
  // Read the first 128 bytes (the free/in-use block information)
  // Scan the list to make sure you have sufficient free blocks to
  //   allocate a new file of this size

  // Step 2: we look for a free inode on disk
  // Read in an inode
  // Check the "used" field to see if it is free
  // If not, repeat the above two steps until you find a free inode
  // Set the "used" field to 1
  // Copy the filename to the "name" field
  // Copy the file size (in units of blocks) to the "size" field

  // Step 3: Allocate data blocks to the file
  // for(i=0;i<size;i++)
  // Scan the block list that you read in Step 1 for a free block
  // Once you find a free block, mark it as in-use (Set it to 1)
  // Set the blockPointer[i] field in the inode to this block number.
  // end for

  // Step 4: Write out the inode and free block list to disk
  // Move the file pointer to the start of the disk file
  // Write out the 128 byte free block list to the disk file
  // Move the file pointer to the position on disk where this inode was stored
  // Write out the inode to the disk file

  return 1;
}  // End Create

int myFileSystem::delete_file(char name[8]) {
  if(!disk.is_open()){
    return -1;
  }
  disk.seekg(128, disk.beg);
  char* inode = new char[sizeof(idxNode)];
  int position = -1;
  idxNode* node;
  for(int i = 0; i < 16; i++){
    disk.read(inode, sizeof(idxNode));
    node = (idxNode *)inode;
    if((*node).used != 1){
      continue;
    }
    else if(strcmp((*node).name, name) == 0){
      position = i;
      break;
    }
  }
  if(position == -1){
    return -1;
  }

  disk.seekg(0, disk.beg);
  char* freeBlock = new char[128];
  disk.read(freeBlock, 128);
  for(int i = 0; i < (*node).size; i++){
    freeBlock[(*node).blockPointers[i]] = 0;
  }
  
  (*node).used = 0;

  disk.seekg(0, disk.beg);
  disk.write(freeBlock, 128);
  disk.seekg(128 + (sizeof(idxNode)*position), disk.beg);
  disk.write((char*)node, sizeof(idxNode));
  // Delete the file with this name
  // Step 1: Locate the inode for this file
  // Move the file pointer to the 1st inode (129th byte)
  // Read in an inode
  // If the inode is free, repeat above step.
  // If the inode is in use, check if the "name" field in the
  //   inode matches the file we want to delete. If not, read the next
  //   inode and repeat

  // Step 2: free blocks of the file being deleted
  // Read in the 128 byte free block list (move file pointer to start
  //   of the disk and read in 128 bytes)
  // Free each block listed in the blockPointer fields as follows:
  // for(i=0;i< inode.size; i++)
  // freeblockList[ inode.blockPointer[i] ] = 0;

  // Step 3: mark inode as free
  // Set the "used" field to 0.

  // Step 4: Write out the inode and free block list to disk
  // Move the file pointer to the start of the file
  // Write out the 128 byte free block list
  // Move the file pointer to the position on disk where this inode was stored
  // Write out the inode

  return 1;
}  // End Delete

int myFileSystem::ls() {
  std::cout << "try ls" << std::endl;
  if(!disk.is_open()){
    return -1;
  }
  disk.seekg(128, disk.beg);
  char* nodes = new char[sizeof(idxNode)];
  for(int i = 0; i < 16; i++){
    if(!disk.read(nodes, sizeof(idxNode))){
      return -1;
    }
    idxNode node = *(idxNode*)nodes;
    if(node.used == 1){
      std::cout << node.name + ' ' + node.size << std::endl;
    }
  }
  // List names of all files on disk
  // Step 1: read in each inode and print
  // Move file pointer to the position of the 1st inode (129th byte)
  // for(i=0;i<16;i++)
  // Read in an inode
  // If the inode is in-use
  // print the "name" and "size" fields from the inode
  // end for

  return 0;
}  // End ls

int myFileSystem::read(char name[8], int blockNum, char buf[1024]) {
  std::cout << "try read" << std::endl;
  if(!disk.is_open()){
    return -1;
  }
  // read this block from this file
  disk.seekg(128, disk.beg);
  idxNode node;
  char* inodes = new char[sizeof(idxNode)];
  for(int i = 0; i < 16; i++){
    if(!disk.read(inodes, sizeof(idxNode))){
      return -1;
    }
    node = *(idxNode*) inodes;
    if(node.used != 1){
      continue;
    }
    if(strcmp(strtok(node.name, " "), name) == 0 && blockNum < node.size){
      disk.seekg(node.blockPointers[blockNum] * 1024, disk.beg);
      disk.read(buf, 1024);
      return 1;
    }
  }
  // Step 1: locate the inode for this file
  // Move file pointer to the position of the 1st inode (129th byte)
  // Read in an inode
  // If the inode is in use, compare the "name" field with the above file
  // If the file names don't match, repeat

  // Step 2: Read in the specified block
  // Check that blockNum < inode.size, else flag an error
  // Get the disk address of the specified block
  // That is, addr = inode.blockPointer[blockNum]
  // Move the file pointer to the block location (i.e., to byte #
  //   addr*1024 in the file)

  // Read in the block => Read in 1024 bytes from this location
  //   into the buffer "buf"

  return -1;
}  // End read

int myFileSystem::write(char name[8], int blockNum, char buf[1024]) {
  std::cout << "try write" << std::endl;
  if(!disk.is_open()){
    return -1;
  }
  disk.seekg(128, disk.beg);
  idxNode node;
  char* inodes = new char[sizeof(idxNode)];
  for(int i = 0; i < 16; i++){
    if(!disk.read(inodes, sizeof(idxNode))){
      return -1;
    }
    node = *(idxNode*) inodes;
    if(node.used != 1){
      continue;
    }
    if(strcmp(strtok(node.name, " "), name) == 0){
      if(blockNum >= node.size){
        return -1;
      }
      disk.seekg(node.blockPointers[blockNum] * 1024, disk.beg);
      disk.write(buf, 1024);
      return 1;
    }
  }
  // write this block to this file
  // Step 1: locate the inode for this file
  // Move file pointer to the position of the 1st inode (129th byte)
  // Read in a inode
  // If the inode is in use, compare the "name" field with the above file
  // If the file names don't match, repeat

  // Step 2: Write to the specified block
  // Check that blockNum < inode.size, else flag an error
  // Get the disk address of the specified block
  // That is, addr = inode.blockPointer[blockNum]
  // Move the file pointer to the block location (i.e., byte # addr*1024)

  // Write the block! => Write 1024 bytes from the buffer "buff" to
  //   this location

  return -1;
}  // end write

int myFileSystem::close_disk() {
  if(!disk.is_open()){
    return 1;
  }
  disk.close();
  if(disk.fail()){
    return 1;
  }
  return -1;
}
