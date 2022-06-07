// Returns a random int i where min <= i < max
function randomInt(min, max) { 
  return Math.floor(Math.random() * (max - min)) + min;
}

//returns a boolean indicating wether there are any duplicates at a given index of an array of arrays
function isNotDuplicate(index, arr, key){
  let dupe = [];
  if(key === 'company'){
    for(let i = 0; i < arr.length; ++i){
     if(dupe.includes(arr[i].company)){
        return false;
      }
     dupe.push(arr[i].company);
    }
    return true;
  }
  for(let i = 0; i < arr.length; ++i){
    if(dupe.includes(arr[i].candidate)){
      return false;
    }
    dupe.push(arr[i].candidate);
  }
  return true;
}

function companyPrefArray(companies, hires){
  let pref = [];
  for(let i = 0; i < hires.length; ++i){
    pref.push(0);
  }
  for(let i = hires.length - 1; i >= 0; --i){
    let indexOfComp = companies[hires[i].company].indexOf(hires[i].candidate);
    if(indexOfComp !== -1){
      pref[hires[i].company] = companies[hires[i].company].slice(0, indexOfComp);
    }
  }
  return pref;
}

function candidatePrefArray(candidates, hires){
  let pref = [];
  for(let i = 0; i < hires.length; ++i){
    pref.push(0);
  }
  for(let i = hires.length - 1; i >= 0; --i){
    let indexOfCand = candidates[hires[i].candidate].indexOf(hires[i].company);
    if(indexOfCand !== -1){
      pref[hires[i].candidate] = candidates[hires[i].candidate].slice(0, indexOfCand);
    }
  }
  return pref;
}

//returns wether or not a given pair is stable or not
function isStable(companies, candidates, hires, pair){
  let x = hires[pair].candidate;
  let y = hires[pair].company;
  let pairCandPref = candidates[x].slice(0, candidates[x].indexOf(y));
  let compPref = companyPrefArray(companies, hires);
  for(let i = 0; i < pairCandPref.length; ++i){
    if(compPref[pairCandPref[i]].includes(x)){
      return false;
    }
  }
  let pairCompPref = companies[y].slice(0, companies[y].indexOf(x));
  let candPref = candidatePrefArray(candidates, hires);
  for(let i = 0; i < pairCompPref.length; ++i){
    if(candPref[pairCompPref[i]].includes(y)){
      console.log('comp fail');
      return false;
    }
  }
  return true;
}

//generateInput(n: number) -> number[][]
function generateInput(n){
  let input = [];
  for(let i = 0; i < n; ++i){
    let preferences = [];
    let max = n;
    let min = 0;
    for(let j = 0; j < n; ++j){
      let rand = randomInt(0, max);
      while(preferences.includes(rand)){
        rand = randomInt(min, max);
      }
      if(rand === max){max = rand;}
      else if(rand === min){min = rand;}
      preferences.push(rand); 
    }
    input.push(preferences);
  }
  return input;
}

function oracle(f) { 
  let numTests = 100;    // Change this to some reasonably large value 
  for (let i = 0; i < numTests; ++i) {
    let n = 20;  // Change this to some reasonable size
    let companies = generateInput(n);
    let candidates = generateInput(n);
    console.log(companies);
    console.log(candidates);
    let hires = f(companies, candidates);
    console.log(hires);
    test('Hires length is correct', function() {
      assert(companies.length === hires.length);
    });


    test('no company hires two condidates', function() {
      assert(isNotDuplicate(0, hires, 'company'));
    });

    test('no candidate is hired twice', function() {
      assert(isNotDuplicate(1, hires, 'candidate'));
    });

    test('each pair is stable', function() {
      let stable = true;
      for(let j = 0; j < hires.length; ++j){
        if(!isStable(companies, candidates, hires, j)){
          stable = false;
        }
      }
      assert(stable);
    });
  }
}

oracle(chaff1);
oracle(wheat1);