var pLimit = 10;
var pOffset = 0;
var pCount = 0;
var pUrl = "/bin/servlet/product/";
$(document).ready(function() {

	getProduct(getUrl(pUrl, pOffset, pLimit), "", 0, 1000000000);

	$('.search').click(function() {
		search(getUrl(pUrl, pOffset, pLimit));
	});

    $("#checkAll").click(function(){
        $('input:checkbox').not(this).prop('checked', this.checked);
    });

    $('#action').click(function() {
		deleteSelectedProducts();
	});

});

<!--Delete product checked.-->

checkboxed($(".checkbox"));
function checkboxed(item) {
	item.change(function(){
        if($("#checkbox").length == $("#checkbox :checked").length){
            $('#checkAll').prop('checked', true);
        }else{
            $('#checkAll').prop('checked', false);
        }
	});
}


function deleteSelectedProducts(){
    var list_id = [];
    $('#checkboxs :checked').each(function() {
        list_id.push($(this).val());
    });
    $.ajax({
		type : "POST",
		dataType : "json",
		url : pUrl,
		data : {
			list_id : String(list_id)
		},
		success : function(data) {
			if (data.status == "successed") {
                window.location.href = "products.html"
			} else {
				showMessage('Message', 'Delete failed');
			}
		},
		error : function(data) {
			showMessage('Message', 'Server Error');
		}
	});
}

<!--Delete a product-->

deleteProduct($('.deleteProduct'));
function deleteProduct(item) {
	item.click(function() {
		var id = $(this).attr('attr-id');
		$.confirm({
			title : 'Confirm Delete',
			content : 'Do you want to delete this product?',
			buttons : {
				Yes : {
					btnClass: 'btn-primary',
					action: function() {
						$.ajax({
							url : pUrl+'?id=' + id,
							type : "DELETE",
							dataType : 'json',
							success : function(data) {
								if (data.status == "successed") {
									location.reload();
								} else {
									showMessage('Message', 'Delete product failed');
								}
							},
							error : function() {
								showMessage('Message', 'Server Error');
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


<!--Search and get products.-->

function search(url){
    var key_word = $.trim($('#key-word').val());
    var price_min = $('#price-min').val();
    var price_max = $('#price-max').val();
    getProduct(url, key_word, price_min, price_max);
}

function getProduct(url, key_word, price_min, price_max) {
    console.log(url);
	$.ajax({
		type : "GET",
		dataType : "json",
		url : url,
		data : {
			key_word : key_word,
			price_min : price_min,
			price_max : price_max
		},
		success : function(data) {
            console.log(data);
        	setProductPageData(data);
            hideShowButtonNextPrevious("#btn-next","#btn-previous", pCount, pOffset, pLimit);
            setProductNumberPage(pCount, pOffset, pLimit);
            var html = simpleTemplating(data.result);
            $('#listProduct').html(html);

            detailProduct($('.detailProduct'));
            deleteProduct($('.deleteProduct'));
            checkboxed($(".checkbox"));
		}
	});
}

<!--Paging.-->

function setProductPageData(data){
    pOffset = data.offset;
    pLimit = data.limit;
    pCount = data.count;
}

function pPrevious(){
    search(getPreviousUrl(pUrl, pOffset, pLimit));
}

function pNext(){
	search(getNextUrl(pUrl, pCount, pOffset, pLimit));
}

function setProductNumberPage(count, offset, limit){
    $('#page-number').text(getDisplay(count, offset, limit));
    $('#page-number-bottom').text(getDisplay(count, offset, limit));
}

<!--Edit a product.-->

detailProduct($('.detailProduct'));
function detailProduct(item) {
	item.click(function() {
		var id = $(this).attr('attr-id');

		$.ajax({
			url : pUrl,
			type : "GET",
			dataType : 'json',
			data : {
				id : id
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
        $('#modalEdit').modal('show');
	});
}

function editProduct() {
	var id = $('#productID').text();
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
		type : "PUT",
		dataType : "json",
		url : pUrl,
		data : {
			id : id,
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
				showMessage('Message', 'Edit product failed');
			}
		},
		error : function() {
			$("#modalEdit").modal("toggle");
			showMessage('Message', 'Server Error');
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
        htmlString += '<div class="row" id="checkboxs" style="'+styleItem(count++)+'">';
		htmlString += '<div class="col-md-1"><input class="checkbox" type="checkbox" id="checkbox" value="' + item.id + '"/></div>';
		htmlString += '<div class="col-md-2 detailProduct" attr-id="' + item.id+ '">'+name+'</div>';
		htmlString += '<div class="col-md-2">'+ item.company +'</div>';
        htmlString += '<div class="col-md-2 text-right" style="'+stylePrice()+'">'+ convertPrice(item.price) +'</div>';
		htmlString += '<div class="col-md-4">'+ description +'</div>';
		htmlString += '<div class="col-md-1">'
						+'<label class="far fa-times-circle icon-delete-item deleteProduct" attr-id="' + item.id+ '"></label>'
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
    if(count%2!=0){
		style += 'background-color: #fff;';
    }else{
		style += 'background-color: rgba(0,0,0,.03);';
    }
    style += 'border: 1px solid rgba(0,0,0,.125);';
    style += 'padding-bottom: 3px;';
    style += 'padding-top: 3px;';
    style += 'margin-bottom: 5px;';
    style += 'align-items: center;';
    return style;
}

function stylePrice(){
    var style = '';
    style += 'color:#cc1010;';
    style += 'font-style: italic;';
    return style;
}