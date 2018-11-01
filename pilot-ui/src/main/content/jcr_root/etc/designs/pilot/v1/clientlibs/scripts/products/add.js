function addproduct() {
	var config = false;
	var name = $.trim($('#name').val());
	var quantity = $.trim($('#quantity').val());
	var price = $.trim($('#price').val());
	var company = $.trim($('#company').val());
	var description = $.trim($('#description').val());
    if(checkInvalid('name','Input required') || 
       checkInvalid('quantity','1-1000',1,1000,true) || 
       checkInvalid('price','1,000 VND - 1,000,000,000 VND',1000,1000000000,true) ||
       checkInvalid('company','Need to choose a company')){
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