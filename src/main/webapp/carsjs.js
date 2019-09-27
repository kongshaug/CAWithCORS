/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


document.getElementById("getall").onclick = fetchAll;
document.getElementById("sort").onclick = sort;
document.getElementById("filterYear").onclick = filterByYear;
document.getElementById("filterModel").onclick = filterByModel;
document.getElementById("filterPrice").onclick = filterByPrice;
document.getElementById("filterMake").onclick = filterByMake;
document.getElementById("back").onclick = function () {
    takeToNewPage("index.html");
};

fetchAll();

function takeToNewPage(url) {
    location.replace(url);
}



function fetchAll()
{
    fetch("/CA1/api/car/all")
            .then(res => res.json()) //in flow1, just do it
            .then(data => {
                // Inside this callback, and only here, the response data is available
                var cars = data.map(car => "<tr><td>" + car.id
                            + "</td><td>" + car.make_year + "</td>"
                            + "<td>" + car.make + "</td><td>"
                            + car.model + "</td><td>" + car.price + "</td></tr>");
                document.getElementById("tablebody").innerHTML = cars.join("\n");
            });
}

function sort()
{
    fetch("/CA1/api/car/all")
            .then(res => res.json()) //in flow1, just do it
            .then(data => {
                var dropdown = document.getElementById("dropdown").value;
                if (dropdown === "year")
                {
                    data.sort(function (a, b) {
                        return a.make_year - b.make_year;
                    });
                } else if (dropdown === "price")
                {
                    data.sort(function (a, b) {
                        return a.price - b.price;
                    });
                } else if (dropdown === "model")
                {
                    data.sort(function (a, b)
                    {
                        if (a.model < b.model) {
                            return -1;
                        }
                        if (a.model > b.model) {
                            return 1;
                        }
                        return 0;
                    }
                    );
                } else if (dropdown === "make")
                {
                    data.sort(function (a, b)
                    {
                        if (a.make < b.make) {
                            return -1;
                        }
                        if (a.make > b.make) {
                            return 1;
                        }
                        return 0;
                    }
                    );
                }

                var sortCars = data.map(car => "<tr><td>" + car.id
                            + "</td><td>" + car.make_year + "</td>"
                            + "<td>" + car.make + "</td><td>"
                            + car.model + "</td><td>" + car.price + "</td></tr>");
                document.getElementById("tablebody").innerHTML = sortCars.join("\n");
            });
}
;
function filterByYear()
{
    fetch("/CA1/api/car/all")
            .then(res => res.json()) //in flow1, just do it
            .then(data => {
                var input = document.getElementById("input").value;
                const byYear = data.filter(car => car.make_year >= input);
                var filterCars = byYear.map(car => "<tr><td>" + car.id
                            + "</td><td>" + car.make_year + "</td>"
                            + "<td>" + car.make + "</td><td>"
                            + car.model + "</td><td>" + car.price + "</td></tr>");
                document.getElementById("tablebody").innerHTML = filterCars.join("\n");
            });
}
;
function filterByModel()
{
    fetch("/CA1/api/car/all")
            .then(res => res.json()) //in flow1, just do it
            .then(data => {
                var input = document.getElementById("input").value;
                const byModel = data.filter(car => car.model === input);
                var filterCars = byModel.map(car => "<tr><td>" + car.id
                            + "</td><td>" + car.make_year + "</td>"
                            + "<td>" + car.make + "</td><td>"
                            + car.model + "</td><td>" + car.price + "</td></tr>");
                document.getElementById("tablebody").innerHTML = filterCars.join("\n");
            });
}
;
function filterByPrice()
{
    fetch("/CA1/api/car/all")
            .then(res => res.json()) //in flow1, just do it
            .then(data => {
                var input = document.getElementById("input").value;
                const byPrice = data.filter(car => car.price >= input);
                var filterCars = byPrice.map(car => "<tr><td>" + car.id
                            + "</td><td>" + car.make_year + "</td>"
                            + "<td>" + car.make + "</td><td>"
                            + car.model + "</td><td>" + car.price + "</td></tr>");
                document.getElementById("tablebody").innerHTML = filterCars.join("\n");
            });
}
;
function filterByMake()
{
    var input = document.getElementById("input").value;
    fetch("/CA1/api/car/make/" + input)
            .then(res => res.json())
            .then(data => {
                var filterCars = data.map(car => "<tr><td>" + car.id
                            + "</td><td>" + car.make_year + "</td>"
                            + "<td>" + car.make + "</td><td>"
                            + car.model + "</td><td>" + car.price + "</td></tr>");
                document.getElementById("tablebody").innerHTML = filterCars.join("\n");
            });
}




