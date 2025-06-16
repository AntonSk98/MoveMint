importScripts(
  "https://www.gstatic.com/firebasejs/11.4.0/firebase-app-compat.js"
);
importScripts(
  "https://www.gstatic.com/firebasejs/11.4.0/firebase-messaging-compat.js"
);

firebase.initializeApp({
  apiKey: "AIzaSyBFJgAxH_K0ZW7b1GURChJHrj1piuhVVXM",
  authDomain: "movemint-eea7d.firebaseapp.com",
  projectId: "movemint-eea7d",
  storageBucket: "movemint-eea7d.firebasestorage.app",
  messagingSenderId: "505904605465",
  appId: "1:505904605465:web:c64d122fd1a9edc0117d88",
});

const messaging = firebase.messaging();
