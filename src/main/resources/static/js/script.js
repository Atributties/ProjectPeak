// script.js

// Function to toggle between light and dark themes
function toggleTheme() {
    const themeLink = document.getElementById('theme-style');
    const currentTheme = themeLink.getAttribute('href');
    const lightTheme = '/css/lightTheme.css';
    const darkTheme = '/css/darkTheme.css';

    // Switch between light and dark themes
    if (currentTheme.includes('lightTheme.css')) {
        themeLink.setAttribute('href', darkTheme);
        localStorage.setItem('theme', 'dark'); // Save the theme preference in localStorage
    } else {
        themeLink.setAttribute('href', lightTheme);
        localStorage.setItem('theme', 'light'); // Save the theme preference in localStorage
    }
}


// Check the current theme when the page loads
document.addEventListener('DOMContentLoaded', function() {
    const themeLink = document.getElementById('theme-style');
    const savedTheme = localStorage.getItem('theme');
    const lightTheme = '/css/lightTheme.css';
    const darkTheme = '/css/darkTheme.css';

    // Set the theme based on the saved value or use dark theme as default
    if (savedTheme === 'light') {
        themeLink.setAttribute('href', lightTheme);
    } else {
        themeLink.setAttribute('href', darkTheme);
        localStorage.setItem('theme', 'dark'); // Save the theme preference in localStorage
    }
});
