$(document).ready(function(){

    $(".name").keyup(function(){
    let names = $(".name");
    for(i = 0; i < names.length; i++){
        if($(names[i]).val().length === 0){
            $("#nameError").show();
            return;
        }
    }
    $("#nameError").hide();
    }); 

    $(".address").keyup(function(){
        let address = $(".address");
        for(i = 0; i < address.length; i++){
            if(i === 2 &&(!(/^\d+$/.test($(address[i]).val())) || !($(address[i]).val().length === 5))){
                $("#addressError").show();
                return;
            }
            else if($(address[i]).val().length === 0){
                $("#addressError").show();
                return;
            }
        }
        $("#addressError").hide();
    });

    $(".entree").click(function(){
        let radios = $(this).find("input[type='radio']");
        for(i in radios){
            if($(radios[i]).is(":checked")){
                $("#entreeError").hide();
                return;
            }
        }
    });

    $(".side").click(function(){
        let checkboxes = $(".side").find("input[type='checkbox']:checked");
        let count = checkboxes.length;
        let text = ''
        if(count === 3){
            $("#sideError").hide();
            return;
        }
        else if(count === 1){
            text = count + " is selected";
        }
        else{
            text = count + " are selected";
        }
        $("#sideError").show();
        $("#sideError").text(text);
    });
});


