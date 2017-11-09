<%@include file="header.jsp"%>

		<div class="k2t-body">
		
			<div class="k2t-title-bar">
				<div class="container k2t-wrap">
					<h1 class="main-title k2t-cf7-title" style="font-size:36px;color:#283c5a;">
						login
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
	                               	<input id='username' type="text" placeholder="username" />
	                               	</div>
	                            </div>
	                            
	                            <br> 
	                            
	                            <div class="k2t-gr1-field">
									<div class="div-border">
									<input id='password' type="password" placeholder="Password" />
									</div>
								</div>
								
								<br> 
									
								<button id="loginbutton"> Login </button>

								</center>
	        
							<script>
							
							$('#loginbutton').click( function(e)
									
							{ 
								
								//alert("para:" + $('#username').val() + $('#password').val()); 
								
								$username = $('#username').val();
								$password = $('#password').val();
								
								$.post("LoginLogoutManager?action=login&username="+$username+"&password="+$password, function(responseText) {     

							            if(responseText=="logindone") {   
												
											alert("login done");
											window.location.href = 'index.jsp';
												
										} else {
											
											alert("login failed");	
											
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