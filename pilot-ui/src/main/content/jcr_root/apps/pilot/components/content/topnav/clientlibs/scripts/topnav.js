function showMessage(tile, msg){
    alert(tile+": "+msg);
}

function checkInvalid(inputId,description,minLength=0,maxLength=1000,isNumber=false,positive=false){
    var config = true;
    var notifyId = inputId+"-notify";
	var input 	= document.getElementById(inputId).value;
	var msg 	= "Input is invalid. ("+description+")";
	var color 	= 'red';

     if (input.length > maxLength) {
		setNotifyById(notifyId,msg,color);
        return config;
	}

	if (input.length >= minLength) {
        if(isNumber){
            if(isNaN(input)){
				setNotifyById(notifyId,msg,color);
                return config;
            }
            if (positive && parseInt(input) <=-1){
                setNotifyById(notifyId,msg,color);
                return config;
            }
        }

		msg 	= '';
		color 	= 'blue';
        config = false;
	}
	setNotifyById(notifyId,msg,color);
    return config;
}
function setNotifyById(id, msg, color){
		document.getElementById(id).innerHTML = msg;
		document.getElementById(id).style.color = color;
}
