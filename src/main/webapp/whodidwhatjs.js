/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

document.getElementById("back").onclick = function () {
    takeToNewPage("index.html");
};

document.getElementById("contract").onclick = function () {
    takeToNewPage("contract.html");
};


function takeToNewPage(url) {
    location.replace(url);
}