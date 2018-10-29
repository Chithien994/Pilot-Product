<%@include file="/libs/foundation/global.jsp"%>
<cq:includeClientLib categories="pilot-clientlibs" />
<cq:includeClientLib categories="listproduct-clientlibs" />
<jsp:useBean id="companyController" class="com.adobe.training.core.product.controller.CompanyController">
	<jsp:setProperty name="companyController" property="resource" value="<%= resource %>"/>
</jsp:useBean>
<c:set var="companies" value="${companyController.companiesList}" />
<div class="container">
	<div class="row">
		<div class="col-md-12">
            <form class="form-inline">
                <div class="form-group mb-2 margin-right-30">
                    <input type="text" class="form-control input-search" placeholder="Search name, company, description" id="keywork">
                </div>
                <div class="form-group mx-sm-3 mb-2 margin-right-30">
                    <label for="price-min">Price from: </label> <select
                    class="form-control" id="price-min">
                    <option value="0">----- Select price -----</option>
                    <option value="1000000">100.000 VND</option>
                    <option value="1000000">1.000.000 VND</option>
                    <option value="1000000">5.000.000 VND</option>
                    </select>
                </div>
                <div class="form-group mx-sm-3 mb-2 margin-right-30">
                    <label for="price-max">To: </label> <select class="form-control" id="price-max">
                    <option value="999999999">----- Select price -----</option>
                    <option value="1000000">1.000.000 VND</option>
                    <option value="3000000">5.000.000 VND</option>
                    <option value="3000000">10.000.000 VND</option>
                    </select>
                </div>
                <button type="button" class="search btn btn-primary">Search</button>
            </form>
			<div class="card">
				<div class="card-header">
					<div class="row">
						<div class="col-md-1"><input type="checkbox" class="group-checkbox" /></div>
						<div class="col-md-2">Name</div>
						<div class="col-md-2">Company</div>
                        <div class="col-md-2 text-right">Price   </div>
						<div class="col-md-3">Description</div>	
						<div class="col-md-2 text-center">Option</div>
					</div>
				</div>
				<div class="card-body">
					<div id="listProduct">		
					</div>
				</div>					
				<div class="card-footer">
					<button class="btn btn-success" onclick="window.location.href='add-product.html'">Add Product</button>
				</div>
			</div>
		</div> 
	</div>
</div>
	<!-- Modal -->
	<div class="modal fade" id="modalEdit" role="dialog"
		data-backdrop="static">
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
							<div class="col-sm-12">
								<button type="button" class="btn btn-default" onclick="resetform()">Clear</button>
								<button type="button" class="btn btn-primary submitEdit">Submit</button>
							</div>
						</div>
                    </div>
                </form>
			</div>
		</div>
	</div>
	<div id="message" class="alert" style="display: none;"></div>
</div>