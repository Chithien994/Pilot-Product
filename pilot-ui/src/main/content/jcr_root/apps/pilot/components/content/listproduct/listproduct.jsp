<%@include file="/libs/foundation/global.jsp"%>
<cq:includeClientLib categories="pilot-v1-clientlibs"/>
<jsp:useBean id="companyController" class="com.adobe.training.core.product.controller.CompanyController">
	<jsp:setProperty name="companyController" property="resource" value="<%= resource %>"/>
</jsp:useBean>
<c:set var="companies" value="${companyController.companiesList}" />
<div class="container">
    <h1>Select product to change</h1>
	<div class="row">
		<div class="col-md-12">
            <form class="form-inline search-style">
                <div class="form-group">
                    <label class="fa fa-search"></label> 
                    <input type="text" class="input ml-sm-2" placeholder="Search" title="Input Name or Company or Description" id="keywork">
                </div>
                <div class="form-group ml-sm-4">
                    <label for="price-min">Price from: </label> 
                    <select class="input ml-sm-3" id="price-min">
                        <option value="0">Min price</option>
                        <option value="100000">100.000 VND</option>
                        <option value="1000000">1.000.000 VND</option>
                        <option value="5000000">5.000.000 VND</option>
                        <option value="10000000">10.000.000 VND</option>
                    </select>
                </div>
                <div class="form-group ml-sm-4">
                    <label for="price-max">To: </label>
                    <select class="input ml-sm-3" id="price-max">
                        <option value="1000000000">Max price</option>
                        <option value="100000">100.000 VND</option>
                        <option value="1000000">1.000.000 VND</option>
                        <option value="5000000">5.000.000 VND</option>
                        <option value="10000000">10.000.000 VND</option>
                    </select>
                </div>
                <div class="form-group ml-sm-3">
                    <button type="button" class="button search">Search</button>
                </div>
            </form>
            <form class="form-inline">
                <label>Action: </label>
                <div class="form-group actions">
					<select class="ml-sm-3 input" id="delete_selected">
                        <option value="">----------</option>
                        <option value="delete_selected">Delete selected product</option>
                    </select>
                </div>
                <div class="form-group ml-sm-3">
                    <button type="button" class="button" id="action">Go</button>
                </div>
            </form>
			<div class="card">
				<div class="card-header">
					<div class="row items-center">
						<div class="col-md-1"><input type="checkbox" id="checkAll" /></div>
						<div class="col-md-2">Name</div>
						<div class="col-md-2">Company</div>
                        <div class="col-md-2 text-right">Price</div>
						<div class="col-md-3">Description</div>	
						<div class="col-md-2 text-right" id="page-number">Page</div>
					</div>
				</div>
				<div class="card-body">
					<div id="listProduct">		
					</div>
				</div>					
				<div class="card-footer">
                    <div class="row">
                        <div class="col-md-6">
                            <button class="btn button-default" onclick="window.location.href='add-product.html'">Add Product</button>
                        </div>
                        <div class="col-md-6 items-center">
                            <div class="page-layout">
                                <button type="button" class="button" id="btn-previous" onclick="pPrevious()">Previous</button>
                                <button type="button" class="button" id="btn-next" onclick="pNext()">Next</button>
                            </div>
                        </div>
                	</div>
				</div>
			</div>
            <div class="float-right"><span class="mr-sm-3" id="page-number-bottom"></span></div>
		</div> 
	</div>
</div>
<!-- Modal -->
<div class="modal fade" id="modalEdit" role="dialog" data-backdrop="static">
    <div class="modal-dialog modal-md">
        <!-- Modal content-->
        <div class="modal-content">
            <form enctype="multipart/form-data">
                <div class="modal-header">
                    <div class="modal-title">
                        <h4>Edit Product</h4>
                    </div>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group row">
                        <label class="col-sm-4 control-label">Product ID: </label>
                        <div class="col-sm-8">
                            <label class="control-label"><strong id="productID"></strong></label>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-4 control-label">Product Name: </label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="name" /> 
                            <span id="name-notify"></span>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-4 control-label">Quantity: </label>
                        <div class="col-sm-8">
                            <input type="number" class="form-control" id="quantity" min="0" />
                            <span id="quantity-notify"></span>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-4 control-label">Price: </label>
                        <div class="col-sm-8">
                            <input type="number" class="form-control" id="price" min="0" />
                            <span id="price-notify"></span>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-4 control-label">Company: </label>
                        <div class="col-sm-8">
                            <select class="form-control" id="company">
                                <option value="">----- Select company -----</option>
                                <c:if test = "${companies != null}">
                                    <c:forEach var="item" items="${companies}">
                                        <option value="${item}">${item}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                            <span id="company-notify"></span>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-4 control-label">Description: </label>
                        <div class="col-sm-8">
                            <textarea class="form-control" id="description"></textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="row">
                        <div class="col-md-12 float-right">
                            <button type="button" class="btn btn-default" onclick="clearAllText()">Clear</button>
                            <button type="button" class="btn button-default" onclick="editProduct()">Submit</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div id="message" class="alert" style="display: none;"></div>
</div>