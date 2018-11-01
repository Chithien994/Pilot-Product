function goBack() {
    window.history.back();
}

function showMessage(tile, msg){
    alert(tile+": "+msg);
}

function checkInvalid(inputId,description,min=1,max=1000,isNumber=false){
    var config = true;
    var notifyId = inputId+"-notify";
	var input 	= document.getElementById(inputId).value;
	var msg 	= "Input is invalid. ("+description+")";
	var color 	= 'red';

    if(isNumber){
		if(!isNaN(input)){
			if (parseInt(input) >= min && parseInt(input) <= max){
                msg 	= '';
                color 	= 'blue';
                config 	= false;
            }
        }
    }else{
		if (input.length >= min && input.length <= max){
            msg 	= '';
            color 	= 'blue';
            config 	= false;
        }
    }

	setNotifyById(notifyId,msg,color);
    return config;
}
function setNotifyById(id, msg, color){
		document.getElementById(id).innerHTML = msg;
		document.getElementById(id).style.color = color;
}

<!--Paging.-->

function getPreviousUrl(url, offset, limit){

	offset = offset - limit;
    if(offset <= 0){
		offset = 0;
    }
 	return getUrl(url, offset, limit);
}

function getNextUrl(url, count, offset, limit){
    console.log(offset);
	offset = offset + limit;
    if(offset >= count){
		offset = count;
    }
 	return getUrl(url, offset, limit);
}

function getUrl(url, offset, limit){
	url = url.concat("?offset=",offset);
    url = url.concat("&limit=",limit);
 	return url
}

function hideShowButtonNextPrevious(nextId, previousId, count, offset, limit){
    if((offset + limit) < count){
		$(nextId).show();
    }else{
		$(nextId).hide();
    }

    if(offset <= 0){
		$(previousId).hide();
    }else{
		$(previousId).show();
    }
}

function getDisplay(count, offset, limit){
    var display = "";
	var pageTotal = (count/limit).toFixed(0);
	mod = (count%limit);
    if(mod > 0 && mod < 5){
		pageTotal++;
    }
    var pageThis = offset/limit + 1;
    display = display.concat("Page: ",pageThis);
    return display.concat("/",pageTotal);
}