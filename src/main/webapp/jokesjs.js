/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var div = document.getElementById("div");
div.style.textAlign = "center";

fetchAll();

document.getElementById("getJoke").onclick = fetchJoke;
document.getElementById("getRandom").onclick = fetchRandom;
document.getElementById("back").onclick = function () {
    takeToNewPage("index.html");
};
function takeToNewPage(url) {
    location.replace(url);
}

function playAudio(element) {
    setTimeout(function () {
        document.getElementById(element).play();
    }, 2000);
}



function fetchAll()
{
    url = "/CA1/api/joke/all";
    fetch(url)
            .then(res => res.json()) //in flow1, just do it
            .then(data => {
                // Inside this callback, and only here, the response data is available
                var jokes = data.map(joke => "<tr><td>" + joke.id
                            + "</td>" + "<td>" + joke.theJoke + "</td>"
                            + "<td>" + joke.type + "</td>" + "<td>"
                            + joke.rating + "</td></tr>");
                document.getElementById("tablebody").innerHTML = jokes.join("\n");
            });
}

function fetchJoke()
{
    playAudio("laugh");

    var jokeId = document.getElementById("jokeId").value;
    url = "/CA1/api/joke/" + jokeId;
    fetch(url)
            .then(res => res.json()) //in flow1, just do it
            .then(data => {
                var jokeTxt = document.getElementById("jokeTxt");
                jokeTxt.innerText = data.theJoke;

                var txt = document.getElementById("txt");
                txt.innerText = "Id: " + data.id
                        + " - Type: " + data.type
                        + " -  Rating: " + data.rating;

            });
}

function fetchRandom()
{

    playAudio("laugh2");

    url = "/CA1/api/joke/random";
    fetch(url)
            .then(res => res.json()) //in flow1, just do it
            .then(data => {
                var jokeTxt = document.getElementById("jokeTxt");
                jokeTxt.innerText = data.theJoke;

                var txt = document.getElementById("txt");
                txt.innerText = "Id: " + data.id
                        + " -  Type: " + data.type
                        + " -  Rating: " + data.rating;
            });
}

            