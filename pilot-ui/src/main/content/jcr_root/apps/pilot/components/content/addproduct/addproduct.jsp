<%@include file="/libs/foundation/global.jsp"%>
<cq:includeClientLib categories="pilot-clientlibs" />
<cq:includeClientLib categories="addproduct-clientlibs" />
<jsp:useBean id="companyController" class="com.adobe.training.core.product.controller.CompanyController">
	<jsp:setProperty name="companyController" property="resource" value="<%= resource %>"/>
</jsp:useBean>
<c:set var="companies" value="${companyController.companiesList}"/>
<div class="container">
    <form class="form-horizontal" action="#">
        <div class="card">
            <div class="card-header"><strong>Add Product</strong> <small> </small></div>
            <div class="card-body">
                <div class="row d-flex justify-content-center">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="control-label col-sm-offset-2 col-sm-12 text-left" for="name">Product Name</label>
                            <div class="col-sm-12">
                                <input type="text" class="form-control" id="name" placeholder="Product Name" required>
								<span id="name-notify"></span>
                            </div>
                            <label class="control-label col-sm-12 text-left" id="valproductName" style="color: red; display: none">Product name is required!</label>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-offset-2 col-sm-12 text-left" for="quantity" min="0">Quantity</label>
                            <div class="col-sm-12">
                                <input type="number" class="form-control" id="quantity" placeholder="Quantity" required>
                                <span id="quantity-notify"></span>
                            </div>
                            <label class="control-label col-sm-12 text-left" id="valquantity" style="color: red; display: none">Quantity is required!</label>
                            <label class="control-label col-sm-12 text-left" id="valmaxquantity" style="color: red; display: none">Max quantity is 9999!</label>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-offset-2 col-sm-12 text-left" for="price" min="0">Price</label>
                            <div class="col-sm-12">
                                <input type="number" class="form-control" id="price" placeholder="Price" required>
                                <span id="price-notify"></span>
                            </div>
                            <label class="control-label col-sm-12 text-left" id="valprice" style="color: red; display: none">Price is required!</label>
                            <label class="control-label col-sm-12 text-left" id="valmaxprice" style="color: red; display: none">Max price is 999999999!</label>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-offset-2 col-sm-12 text-left" for="company">Company</label>
                            <div class="col-sm-12">
                                <select class="form-control" id="company" required>
                                    <option value="">----- Select company -----</option>
                                    <c:if test = "${companies != null}">
                                        <c:forEach var="item" items="${companies}">
                                            <option value="${item}">${item}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                                <span id="company-notify"></span>
                            </div>
                            <label class="control-label col-sm-12 text-left" id="valcompany" style="color: red; display: none">Company is required!</label>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-offset-2 col-sm-12 text-left" for="description">Description</label>
                            <div class="col-sm-12">
                                <textarea class="form-control" id="description" placeholder="Description"></textarea>
                                <span id="description-notify"></span>
                            </div>
                        </div>
    
                    </div>
                </div>
                <div id="message" class="alert" style="display: none;"></div>
            </div>
            <div class="card-footer">
                <div class="row">
                    <div class="col-md-6">
                        <button class="btn btn-success" onclick="window.location.href=''">Back</button>
                    </div>
                    <div class="col-md-6 float-right">
                        <div class="float-right">
                            <button type="button" class="btn btn-default" onclick="resetform()">Clear</button>
                            <button type="button" class="btn btn-primary" onclick="addproduct()">Add</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>