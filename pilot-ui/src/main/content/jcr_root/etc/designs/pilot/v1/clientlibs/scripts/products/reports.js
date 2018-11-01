var rLimit = 2;
var rOffset = 0;
var rCount = 0;
var rUrl = "/bin/report/";
$(document).ready(function() {
	getReport(getUrl(rUrl,rOffset,rLimit),"");

    $('.report-search').click(function() {
		reportSearch(getUrl(rUrl,rOffset,rLimit));
	});
});



function setReportPageData(data){
    rOffset = data.offset;
    rLimit = data.limit;
    rCount = data.count;
}


function rPrevious(){
    reportSearch(getPreviousUrl(rUrl, rOffset, rLimit));
}

function rNext(){
	reportSearch(getNextUrl(rUrl, rCount, rOffset, rLimit));
}

function setReportNumberPage(count, offset, limit){
    $('#report-page-number').text(getDisplay(count, offset, limit));
    $('#report-page-number-bottom').text(getDisplay(count, offset, limit));
}

function reportSearch(url){
    var key_word = $.trim($('#report-key-word').val());
    getReport(url, key_word);
}

function getReport(url, key_word) {
    console.log(url);
	$.ajax({
		type : "GET",
		dataType : "json",
		url : url,
        data : {
			key_word : key_word
		},
		success : function(data) {
            console.log(data);
            setReportPageData(data);
            hideShowButtonNextPrevious("#btn-next","#btn-previous", rCount ,rOffset,rLimit);
            setReportNumberPage(rCount, rOffset, rLimit);
			$("#listReport").html("");
			$("#listReport").append(reportsTemplating(data.result));
		}
	});
}

function reportsTemplating(data) {
	var htmlString = '';
    var count = 0;
	$.each(data, function(index, item){
        var file = "/var/pilot/reports/report_"+item.value+".csv";
        htmlString += '<div class="row" id="checkboxs" style="'+styleItem(count++)+'">';
		htmlString += '<div class="col-md-6" attr-id="' + item.date+ '">'+item.date+'</div>';
		htmlString += '<div class="col-md-5">'
        				+ '<a href="'+file+'">Download</a>'
            			+'</div>';
         htmlString += '</div>';
	});
	return htmlString;
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
