class FluentRestaurants{

  constructor(jsonData){
    this.data = jsonData;
  }

  fromState(stateStr){
    let rest = [];
    for(let i = 0; i < this.data.length; ++i){
      let obj = lib220.getProperty(this.data[i], 'state');
      if(obj.found && obj.value === stateStr){
        rest.push(this.data[i]);
      }
    }
    return new FluentRestaurants(rest);
  }

  ratingLeq(number){
    let rest = [];
    for(let i = 0; i < this.data.length; ++i){
      let obj = lib220.getProperty(this.data[i], 'stars');
      if(obj.found && obj.value <= number){
        rest.push(this.data[i]);
      }
    }
    return new FluentRestaurants(rest);
  }

  ratingGeq(number){    
    let rest = [];
    for(let i = 0; i < this.data.length; ++i){
      let obj = lib220.getProperty(this.data[i], 'stars');
      if(obj.found && obj.value >= number){
        rest.push(this.data[i]);
      }
    }
    return new FluentRestaurants(rest);
  }

  category(categoryStr){
    let rest = [];
    for(let i = 0; i < this.data.length; ++i){
      let obj = lib220.getProperty(this.data[i], 'categories');
      if(obj.found){
        for(let j = 0; j < obj.value.length; ++j){
          if(obj.value[j] === categoryStr){
            rest.push(this.data[i]);
          }
        }
      }
    }
    return new FluentRestaurants(rest);
  }

  hasAmbience(ambienceStr){
    let rest = [];
    for(let i = 0; i < this.data.length; ++i){
      let obj = lib220.getProperty(this.data[i], 'attributes');
      if(obj.found){
        let amb = lib220.getProperty(obj.value, 'Ambiance');
        if(amb.found){
          let ambi = lib220.getProperty(amb.value, 'ambienceStr');
          if(ambi.found && ambi.value){
            rest.push(this.data[i]);
          }
        }
      }
    }
    return new FluentRestaurants(rest);
  }

  bestPlace(){
    let best = {};
    for(let i = 0; i < this.data.length; ++i){
      let b = lib220.getProperty(best, 'stars');
      let obj = lib220.getProperty(this.data[i], 'stars');
      if(!b.found && obj.found){
        best = this.data[i];
      }
      else if(b.found && !obj.found){
        continue;
      }
      else if((b.found && obj.found) && (b.value === obj.value)){
        let bestRev = lib220.getProperty(best, 'review_count');
        let objRev = lib220.getProperty(this.data[i], 'review_count');
        console.log(bestRev.value);
        if(!bestRev.found && objRev.found){
          best = this.data[i];
        }
        else if(bestRev.found && !objRev.found){
          continue;
        }
        else if((bestRev.found && objRev.found) && (bestRev.value < objRev.value)){
          console.log("hi");
          best = this.data[i];
        } 
      }
      else if((b.found && obj.found) && b.value < obj.value){
        best = this.data[i];
      }
    }
    let bestName = lib220.getProperty(best, 'name');
    return best;
  }
}
const testData = 
  [ 
    {   
      name: "Applebee's",
      state: "NC",    
      stars: 4,    
      review_count: 6,
    },  
    {    
      name: "China Garden",    
      state: "NC",    
      stars: 4,    
      review_count: 10,
    },
    {    
      name: "Beach Ventures Roofing",    
      state: "AZ",    
      stars: 3,    
      review_count: 30,
    }, 
    {    
      name: "Alpaul Automobile Wash",    
      state: "NC",    
      stars: 3,    
      review_count: 30,
    }
  ]
  test('fromState filters correctly', function() {
    let tObj = new FluentRestaurants(testData);
    let list = tObj.fromState('NC').data;    
    assert(list.length === 3);
    assert(list[0].name === "Applebee's");    
    assert(list[1].name === "China Garden");    
    assert(list[2].name === "Alpaul Automobile Wash");});

  test('bestPlace tie-breaking', function() {
      let tObj = new FluentRestaurants(testData);
      let place = tObj.fromState('NC').bestPlace();
      assert(place.name === 'China Garden');});

function foo() {
      let tObj = new FluentRestaurants(testData);
      let place = tObj.fromState('NC').bestPlace();
}

foo();