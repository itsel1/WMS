/**
 * 
 */
var isSidebarClosed = false;
function fn_toggleSidebar() {
	var sideMenu = document.querySelector(".sidebar-menu");
	var content = document.getElementById('displayArea');
	var header = document.querySelector('header');
	
	sideMenu.classList.toggle("close");
	if (sideMenu.classList.contains('close')) {
		isSidebarClosed = true;
	} else {
		isSidebarClosed = false;
	}
	
	if (isSidebarClosed) {
		content.classList.add('display-area-shrink');
		header.classList.add('header-expand');
	} else {
		content.classList.remove('display-area-shrink');
		header.classList.remove('header-expand');
	}
}

document.querySelectorAll(".sidebar-menu .sidebar .menu-links .menu-link a").forEach(function(obj) {
	obj.addEventListener("click", function(e) {
		if (e.target.parentNode.children.length > 1) {
			e.target.parentNode.classList.toggle("active");
		}
	});
});