<%@include file="/libs/foundation/global.jsp"%>
<cq:includeClientLib categories="pilot-v1-clientlibs"/>
<div class="container">
    <h1>Select report to download</h1>
	<div class="row">
		<div class="col-md-12">
            <form class="form-inline search-style">
                <div class="form-group">
                    <label class="fa fa-search"></label> 
                    <input type="text" class="input ml-sm-2" placeholder="Search" title="Input Date (yyyy-mm-dd)" id="report-key-word">
                </div>
                <div class="form-group ml-sm-3">
                    <button type="button" class="button report-search">Search</button>
                </div>
            </form>
            <div class="card">
				<div class="card-header">
					<div class="row items-center">
						<div class="col-md-6">Date</div>
						<div class="col-md-4">Download</div>	
						<div class="col-md-2 text-right" id="report-page-number">Page</div>
					</div>
				</div>
				<div class="card-body">
					<div id="listReport">		
					</div>
				</div>					
				<div class="card-footer">
                    <div class="row">
                        <div class="col-md-12 items-center">
                            <div class="page-layout">
                                <button type="button" class="button" id="btn-previous" onclick="rPrevious()">Previous</button>
                                <button type="button" class="button" id="btn-next" onclick="rNext()">Next</button>
                            </div>
                        </div>
                	</div>
                </div>
           	</div>
            <div class="float-right"><span class="mr-sm-3" id="report-page-number-bottom"></span></div>
        </div>
	</div>
</div>
