function selFirst(){
    var first=document.getElementsByName("DeleteBk");
    first[0].selected=true
;}

function selRow(radio){
    var trs=document.getElementsByTagName("tr");
    for(var x=0;x<trs.length;x++){
        trs[x].style.backgroundColor="cyan";
    }
    var tr=radio.parentElement.parentElement;
    tr.style.backgroundColor="red";  
    document.getElementById("delb").disabled=false;
}