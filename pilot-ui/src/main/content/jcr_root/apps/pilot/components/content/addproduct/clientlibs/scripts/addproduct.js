function addproduct() {
	var config = false;
	var name = $.trim($('#name').val());
	var quantity = $.trim($('#quantity').val());
	var price = $.trim($('#price').val());
	var company = $.trim($('#company').val());
	var description = $.trim($('#description').val());
    if(checkInvalid('name','Input required',1) || 
       checkInvalid('quantity','0-9999',1) || 
       checkInvalid('price','1,000 VND - 1,000,000,000 VND',4,10) ||
       checkInvalid('company','Need to choose a company',1)){
			return;
    }


	$.ajax({
		type : "POST",
		dataType : "json",
		url : '/bin/servlet/product',
		data : {
			name : name,
			quantity : quantity,
			price : price,
			company : company,
			description : description
		},
		success : function(data) {
            console.log(data);
			if (data.status == "successed") {
                window.location.href = "products.html"
			} else {
				showMessage('alert alert-danger', 'Add product failed');
			}
		},
		error : function(data) {
            console.log(data);
			showMessage('alert alert-danger', 'Server Error');
		}
	});
}

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