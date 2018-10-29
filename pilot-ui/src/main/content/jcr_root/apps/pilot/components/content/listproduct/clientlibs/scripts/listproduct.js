$(document).ready(function() {

	getProduct('', 0, 9999999999, 1);

	getReport();

	$('.search').click(function() {
		var keywork = $.trim($('#keywork').val());
		var price_min = $('#price-min').val();
		var price_max = $('#price-max').val();
		getProduct(keywork, price_min, price_max, 1);
	});

	$('.submitEdit').click(function() {
		editProduct();
	});

	$('.group-checkbox').change(function() {
		var checkboxes = $(this).closest('form').find(':checkbox');
		checkboxes.prop('checked', $(this).is(':checked'));
	});

	$('.deleteProducts').click(function() {
		deleteMultiProduct();
	});

});

deleteProduct($('.deleteProduct'));
function deleteProduct(item) {
	item.click(function() {
		var id = $(this).attr('attr-id');
		$.confirm({
			title : 'Confirm Delete',
			content : 'Do you want to delete this product!',
			buttons : {
				Yes : {
					btnClass: 'btn-primary',
					action: function() {
						var page = $(".J-paginationjs-page.active").attr('data-num');
						$.ajax({
							url : '/bin/servlet/product?productID=' + id,
							type : "DELETE",
							dataType : 'json',
							success : function(data) {
								if (data.status == "successed") {
									location.reload();
								} else {
									showMessage('alert alert-danger', 'Delete product failed');
								}
							},
							error : function() {
								showMessage('alert alert-danger', 'Server Error');
							}
						});
					}
				},
				No : {
					btnClass: 'btn-danger',
					action: function() {
					}
				}
			}
		});
	});
}

detailProduct($('.detailProduct'));
function detailProduct(item) {
	item.click(function() {
		var id = $(this).attr('attr-id');

		$.ajax({
			url : '/bin/servlet/product',
			type : "GET",
			dataType : 'json',
			data : {
				productID : id
			},
			success : function(data) {
				$('#productID').text(data.id);
				$('#name').val(data.name);
				$('#quantity').val(data.quantity);
				$('#price').val(data.price);
				$('#company').val(data.company);
				$('#description').val(data.description);
			}
		});
		$('#valproductName').hide();
		$('#valquantity').hide();
		$('#valprice').hide();
        $('#modalEdit').modal('show');
	});
}

function getProduct(keywork, price_min, price_max, page) {
	$.ajax({
		type : "GET",
		dataType : "json",
		url : '/bin/servlet/product?search=true',
		data : {
			keywork : keywork,
			price_min : price_min,
			price_max : price_max
		},
		success : function(data) {
            console.log(data);
			var productsList = data.result;
            var html = simpleTemplating(productsList);
            $('#listProduct').html(html);

            detailProduct($('.detailProduct'));
            deleteProduct($('.deleteProduct'));
            checkboxed($(".checkboxes"));
            $('.group-checkbox').prop('checked', false);
		}
	});
}

function editProduct() {
	var page = $(".J-paginationjs-page.active").attr('data-num');
	var productID = $('#productID').text();
	var name = $.trim($('#name').val());
	var quantity = $.trim($('#quantity').val());
	var price = $.trim($('#price').val());
	var company = $.trim($('#company').val());
	var description = $.trim($('#description').val());

	if(checkInvalid('name','Input required',1) || 
       checkInvalid('quantity','0-9999',1,4,true,true) || 
       checkInvalid('price','1,000 VND - 1,000,000,000 VND',4,10) ||
       checkInvalid('company','Need to choose a company',1)){
			return;
    }

	$.ajax({
		type : "PUT",
		dataType : "json",
		url : '/bin/servlet/product',
		data : {
			productID : productID,
			name : name,
			quantity : quantity,
			price : price,
			company : company,
			description : description
		},
		success : function(data) {
			$("#modalEdit").modal("toggle");
            console.log(data);
			if (data.status == "successed") {
				location.reload();
			} else {
				showMessage('alert alert-danger', 'Edit product failed');
			}
		},
		error : function() {
			$("#modalEdit").modal("toggle");
			showMessage('alert alert-danger', 'Server Error');
		}
	});
}

function getReport() {
	$.ajax({
		type : "GET",
		dataType : "json",
		url : '/bin/report',
		success : function(data) {
			var listRes = data.result;
			$("#listReport").html("");
			$.each(listRes, function(index, item) {
				var htmlString = "<a href='/var/pilot/reports/report_"
						+ item.value + ".csv'>Click to download : Date " + item.date + "</a><br>";
				$("#listReport").append(htmlString);
			});
		}
	});
}

checkboxed($(".checkboxes"));
function checkboxed(item) {
	item.change(function(){
		if($(".checkboxes").length == $(".checkboxes:checked").length){
			$('.group-checkbox').prop('checked', true);
		}else{
			$('.group-checkbox').prop('checked', false);
		}
	});
}

function deleteMultiProduct() {
	$.confirm({
		title : 'Confirm Delete',
		content : 'Do you want to delete the seleted products!',
		buttons : {
			Yes : {
				btnClass: 'btn-primary',
				action : function() {
					var page = $(".J-paginationjs-page.active").attr('data-num');
					var list = "";
					$(".checkboxes:checked").each(function() {
						list = list + $(this).attr('attr-id') + ",";
					});
					list = list.substring(0, list.length - 1);
					if($(".checkboxes:checked").length==0){
						showMessage('alert alert-warning', 'Not selected products');
					} else {
						$.ajax({
							url : '/bin/servlet/product',
							type : "POST",
							dataType : 'json',
							data : {
								listProduct : list,
							},
							success : function(data) {
								if (data.status == "successed") {
									location.reload();
									showMessage('alert alert-success', 'Delete products successed');
									$('.group-checkbox').prop('checked', false);
								} else {
									showMessage('alert alert-danger', 'Delete products failed');
								}
							},
							error : function() {
								showMessage('alert alert-danger', 'Server Error');
							}
						});
					}
				}
			},
			No : {
				btnClass: 'btn-danger',
				action: function() {
				}
			}
		}
	});
}

function simpleTemplating(data) {
	var htmlString = '';
	var nameMaxLength = 20;
    var desMaxLength = 30;
    var count = 0;
	$.each(data, function(index, item){
		var name = String(item.name);
        var description = String(item.description);
		name = name.length > nameMaxLength ? name.substring(0, nameMaxLength) + "..." : name;
		description = description.length > desMaxLength ? description.substring(0, desMaxLength) + "..." : description;
        htmlString += '<div class="row" style="'+styleItem(count++)+'">';
		htmlString += '<div class="col-md-1"><input type="checkbox" class="checkboxes" attr-id="' + item.id + '"/></div>';
		htmlString += '<div class="col-md-2" style="'+styleName()+'">'+name+'</div>';
		htmlString += '<div class="col-md-2">'+ item.company +'</div>';
        htmlString += '<div class="col-md-2 text-right" style="'+stylePrice()+'">'+ convertPrice(item.price) +'</div>';
		htmlString += '<div class="col-md-3">'+ description +'</div>';
		htmlString += '<div class="col-md-2" style="color: blue; text-align: center">'
						+'<a style="cursor: pointer;" class="detailProduct" attr-id="' + item.id+ '">Edit</a>'
						+' | <a style="cursor: pointer;" class="deleteProduct" attr-id="' + item.id + '">Delete</a>'
						+'</div>';
         htmlString += '</div>';
	});
	return htmlString;
}

function convertPrice(input){
	var output = [];
    for (i = 0; i < input.length; i++) {
        var a = (input.length-3-i)>0?(input.length-3-i):0;
        var b = input.length-i;
        output.push(input.slice(a,b));
        i+=2;
    }
	return output.reverse()+" đ";
}

function styleItem(count){
    var style = '';
    if(count%2==0){
		style += 'bbackground-color: #fff;';
    }else{
		style += 'background-color: rgba(0,0,0,.03);';
    }
    style += 'border: 1px solid rgba(0,0,0,.125);';
    style += 'padding-bottom: 10px;';
    style += 'padding-top: 10px;';
    style += 'margin-bottom: 5px;';
    return style;
}

function styleName(){
    var style = '';
    style += 'color:mediumblue;';
    style += 'font-weight:500;';
    return style;
}

function stylePrice(){
    var style = '';
    style += 'color:#cc1010;';
    style += 'font-style: italic;';
    return style;
}