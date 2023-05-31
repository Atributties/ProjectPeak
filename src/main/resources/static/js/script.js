// Function to update theme based on saved preference
function updateTheme() {
    const themeLink = document.getElementById('theme-style');
    const logoImage = document.getElementById("logo-img");
    const frontImage = document.getElementById("img-front");
    const savedTheme = localStorage.getItem('theme');
    const lightTheme = '/css/lightTheme.css';
    const darkTheme = '/css/darkTheme.css';

    // Set the theme based on the saved value or use dark theme as default
    if (savedTheme === 'light') {
        themeLink.setAttribute('href', lightTheme);
        frontImage.src = "/images/frontUser-white.png"
        logoImage.src = "/images/logo-md-dark.png";
    } else {
        themeLink.setAttribute('href', darkTheme);
        frontImage.src = "/images/frontUser-dark.png"
        logoImage.src = "/images/logo-md-white.png";
    }
}

// Function to toggle between light and dark themes
function toggleTheme() {
    const themeLink = document.getElementById('theme-style');
    const logoImage = document.getElementById("logo-img");
    const frontImage = document.getElementById("img-front");
    const currentTheme = themeLink.getAttribute('href');
    const lightTheme = '/css/lightTheme.css';
    const darkTheme = '/css/darkTheme.css';

    // Switch between light and dark themes
    if (currentTheme.includes('lightTheme.css')) {
        localStorage.setItem('theme', 'dark'); // Save the theme preference in localStorage
    } else {
        localStorage.setItem('theme', 'light'); // Save the theme preference in localStorage
    }

    // Update theme based on new preference
    updateTheme();
}

// Check the current theme when the page loads
document.addEventListener('DOMContentLoaded', updateTheme);





