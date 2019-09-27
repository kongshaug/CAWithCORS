/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var div = document.getElementById("div");
div.style.margin = "auto";
div.style.width = "60%";
div.style.border = "3px solid #A9A9A9";
div.style.padding = "10px";
div.style.textAlign = "center";

Reloade();

document.getElementById("reloadtable").onclick = Reloade;
document.getElementById("whodidwhat").onclick = function () {takeToNewPage("whodidwhat.html");};
document.getElementById("jokes").onclick = function () {takeToNewPage("jokes.html");};
document.getElementById("git").onclick = function () {takeToNewPage("https://github.com/amalielandt/CA1");};
document.getElementById("cars").onclick = function () {takeToNewPage("cars.html");};

function takeToNewPage(url) {
    location.replace(url);
}

function Reloade() {

    fetch("/CA1/api/student/all")
            .then(res => res.json()) //in flow1, just do it
            .then(data => {
                var studentList = data.map(student => "<tr> <td>" + student.name + "</td>" +
                            "<td> " + student.color + "</td>" +
                            "<td>" + student.gender + "</td>" +
                            "<td>" + student.age + "</td></tr>");

                document.getElementById("tablebody").innerHTML = studentList.join("\n");
            });




}


