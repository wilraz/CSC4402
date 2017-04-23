/*
function myFunction() {
		// body...
		document.getElementById("myDropdown").classList.toggle("show");	
	}
	window.onclick = function(event){

		if(!event.target.matches('.dropbtn')){
			var dropDowns = document.getElementByClassName("dropdownContent");
			var i;
			for(i = 0; i < dropDowns.length; i++){
				var openDropdown = dropDowns[i];
				if(openDropdown.classList.contains('show')){
					openDropdown.classList.remove('show');
				}
			}
		}
	}
*/ 
	$(document).ready(function(){
		$("#alumnisSearch").click(function(){
			$("#myModal").modal();
		});

	});


	

	