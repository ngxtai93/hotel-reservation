<%@include file="header.jsp"%>

		<div class="k2t-body">
		
			<div class="k2t-title-bar">
				<div class="container k2t-wrap">
					<h1 class="main-title k2t-cf7-title" style="font-size:36px;color:#283c5a;">
						Sign up
					</h1>
				</div>
			</div>
			
			
			
			<div class="k2t-content no-sidebar k2t-confirm-content">
				<div class="container k2t-wrap">
					<main class="k2t-main page">
						<div class="page-entry">
						
							
								<center>
	                        
	                        	<br> <br>
	                        
	                        	<div class="k2t-gr1-field">
	                               	<div class="div-border">
	                               	<input id='email' type="text" placeholder="Email*" />
	                               	</div>
	                            </div>
	                            
	                            <br> 
	                            
	                            <div class="k2t-gr1-field">
	                               	<div class="div-border">
	                               	<input id='username' type="text" placeholder="Username*" />
	                               	</div>
	                            </div>
	                            
	                            <br> 
	                            
	                            <div class="k2t-gr1-field">
									<div class="div-border">
									<input id='password' type="password" placeholder="Password*" />
									</div>
								</div>
								
								<br> 
								
								<div class="k2t-gr1-field">
									<div class="div-border">
									<input id='cfp' type="password" placeholder="Confirm Password*" />
									</div>
								</div>
								
								<br> 
								
								
									
								<button id='signupbutton'> Sign up </button>

								</center>
	        
	        
	        				<script>
							
							$('#signupbutton').click( function(e)
									
							{ 
								
								//alert("para:" + $('#username').val() + $('#password').val()); 
								
								$username = $('#username').val();
								$password = $('#password').val();
								$email    = $('#email').val();
								
								$.post("LoginLogoutManager?action=signup&username="+$username+"&password="+$password+"&email="+$email, function(responseText) {     

							            if(responseText=="signupdone") {   
												
											alert("signup done");
											window.location.href = 'index.jsp';
												
										} else {
											
											alert("duplicate username");	
											
										}
											
						            });

							});
							
							
							</script>
							
							
							
						</div>
					</main>
				</div>
			</div>
			
		</div>




<%@include file="footer.jsp" %>