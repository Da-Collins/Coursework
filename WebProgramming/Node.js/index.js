
const express = require('express')
const path = require('path')
const PORT = process.env.PORT || 5000
var entreeList = [{ id: "chickenfriedsteak", name: "Chicken Fried Steak"},
                  { id: "ribeyesteak", name: "Ribeye Steak"},
                  { id: "baconlettucetomatosandwich", name: "Bacon Lettuce and Tomato"},
                  { id: "lentilsoup", name: "Lentil Soup"}
                 ];

function deleteEntry(id){
  for(e in entreeList){
    if(entreeList[e].id === id){
      entreeList.splice(e, 1);
      return true;
    }
  }
  return false;
}

function addEntry(entree){
  entreeList.push(entree);
  return true;
}

express()
  .use(express.static(path.join(__dirname, 'public')))
  .use(express.urlencoded({extended: true}))
  .get('/', function(req, res) {
    res.sendFile(path.join(__dirname + '/configuremenu.html'));
  })
  .get('/configuremenu', function(req, res) {
    res.sendFile(path.join(__dirname + '/public/configuremenu.html'));
  })
  .get('/api/entrees', function(req, res) {
    return res.json(entreeList);
  })
  .delete('/api/entrees/:id', function(req, res){
    if(deleteEntry(req.params.id) === true){
      return res.sendStatus(204);
    }
    else{
      return res.sendStatus(404);
    }
  })
  .post('/api/entrees', function(req, res) { 
    if(addEntry(req.body) === true){
      return res.sendStatus(204);
    }
    else{
      return res.sendStatus(404);
    }
  })
  .listen(PORT, () => console.log(`Listening on ${ PORT }`))

