<%@include file="header.jsp" %>
		
		
		<div class="k2t-body">
			<div class="k2t-title-bar">
				<div class="container k2t-wrap">
					<h1 class="main-title k2t-cf7-title" style="font-size:36px;color:#283c5a;">
						Booking Room
					</h1>
				</div>
			</div>
			<!-- .k2t-title-bar -->
			<div class="k2t-content no-sidebar k2t-confirm-content">
				<div class="container k2t-wrap">
					<main class="k2t-main page">
						<div class="page-entry">
							<div class="k2t-reservation-confirm">
								<div class="k2t-step-f pull-left first">
									<div class="k2t-step-done">
										<a> 1 </a>
									</div>
									<p class="k2t-p-done"> Choose Date </p>
								</div>
								<div class="k2t-step-s pull-left">
									<div class="k2t-step-done">
										<a> 2 </a>
									</div>
									<p class="k2t-p-done"> Choose Room </p>
								</div>
								<div class="k2t-step-f pull-left">
									<div class="k2t-step-current k2t-step-done">
										<a> 3 </a>
									</div>
									<p class="k2t-p-current"> Reservation </p>
								</div>
								<div class="k2t-step-fo pull-left last">
									<div class="k2t-next-step k2t-step-done">
										<a> 4 </a>
									</div>
									<p class="k2t-p-next"> Confirmation </p>
								</div>
							</div>
							<div class="k2t-step3-confirm w100 pull-left">
								<div class="k2t-field-info k2t-info-user pull-left">
									<p class="k2t-title-grfield"> Your Information </p>
									<div class="k2t-gr1-field pull-left">
										<div class="div-border"><input type="text" placeholder="First Name*" /></div>
										<div class="div-border"><input type="text" placeholder="Email*" /></div>
										<div class="div-border"><input type="text" placeholder="Address" /></div>
									</div>
									<div class="k2t-gr2-field pull-right">
										<div class="div-border"><input type="text" placeholder="Last Name*" /></div>
										<div class="div-border"><input type="text" placeholder="Phone*" /></div>
										<div class="div-border"><input type="text" placeholder="Coupon Code" /></div>
									</div>
									<div class="div-border"><textarea placeholder="Special Requirements"></textarea></div>
									<div class="k2t-field-button pull-left w100">
										<a class="pull-left k2t-field-backstep" href="choose-room.jsp"> <i class="zmdi zmdi-long-arrow-left"></i> &nbsp; Back Step </a>
										<a class="pull-right k2t-field-booknow" href="javascript:void(0)">Book Now By Email</a>
									</div>
									<p class="k2t-title-grfield w100 pull-left"> Pay 20% Deposit </p>
									<div class="w100 pull-left k2t-payment">
										<a href="javascript:void(0)" class="col-3"><img src="images/upload/paypal.png" alt="paypal" /></a>
										<a href="javascript:void(0)" class="col-3"><img src="images/upload/payoneer.png" alt="payoneer" /></a>
										<a href="javascript:void(0)" class="col-3"><img src="images/upload/visa.png" alt="visa" /></a>
										<a href="javascript:void(0)" class="col-3"><img src="images/upload/mastercard.png" alt="mastercard" /></a>
									</div>
									<a href="reservation-confirm.jsp" class="pull-left w100 k2t-pay-now"> Pay Now </a>
								</div>
								<div class="k2t-branch-confirm k2t-step3-right pull-right">
									<p class="title-confirm">Your Reservation</p>
									<p class="sub-title-confirm">Please check your information again!</p>
									<div class="k2t-entry-cr">
					
										<p class="k2t-confirm-p"> 
											<span class="k2t-col-f"> Check In: </span>
											<span class="k2t-col-s"> 28/09/2015 </span>
										</p>
										<p class="k2t-confirm-p">
											<span class="k2t-col-f"> Check Out: </span>
											<span class="k2t-col-s"> 06/10/2015 </span>
										</p>
										<p class="k2t-confirm-p"> 
											<span class="k2t-col-f"> Room: </span>
											<span class="k2t-col-s"> King Suite Room </span>
										</p>
										<p class="k2t-confirm-p"> 
											<span class="k2t-col-f"> Adult: </span>
											<span class="k2t-col-s"> 2 </span> 
										</p>
										<p class="k2t-confirm-p"> 
											<span class="k2t-col-f"> Children: </span>
											<span class="k2t-col-s"> 0 </span>
										</p>
									</div>
									<div class="k2t-entry-cr k2t-entry-sub">
										<p class="k2t-confirm-p k2t-total"> 
											<span class="k2t-col-total"> Total </span>
											<span class="k2t-col-price k2t-col-s"> $ 1,280 </span>
										</p>
										<p class="k2t-confirm-p k2t-vat"> 
											<span class="k2t-col-vat"> VAT 10% </span>
											<span class="k2t-col-pvat k2t-col-s"> $ 128 </span>
										</p>
									</div>
									<p class="k2t-confirm-p kt2-grand"> 
										<span class="k2t-grand-total"> Grand Total </span>
										<span class="k2t-col-s"> $ 1,408 </span>
									</p>
									<div class="pull-left w100" style="padding:0 23%; margin-top:50px">
										<a href="javascript:void(0)" class="k2t-btn-editbooking w100 pull-left">Confirm</a>
									</div>
								</div>
							</div>
						</div>
					</main>
				</div>
			</div>
		</div>
		
		
		
		
<%@include file="footer.jsp" %>