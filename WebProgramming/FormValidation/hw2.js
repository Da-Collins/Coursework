function validate(){
    let entrees = document.getElementsByName("entree");
    let valid = false;
    for(i = 0; i < entrees.length; i++){
        if(entrees[i].checked){
            valid = true;
            break;
        }
    }
    if(!valid){
        alert("please choose an entree");
        return;
    }
    let sides = document.getElementsByName("side");
    let count = 0;
    for(i = 0; i < sides.length; i++){
        if(sides[i].checked){
            count = count + 1;
        }
    }
    if(count !== 3){
        alert("please choose exactly 3 sides");
        return;
    }
};