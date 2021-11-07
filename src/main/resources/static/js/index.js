const API_USERS = 'api/v1/users'

let page = 1;

$(document).ready(function () {
    function fill(data) {
        console.log(data);
        data.forEach(function (d) {
            $("#users").append("<tr>" +
                    "<td>" + d.number + "</td>" +
                    "<td>" + d.id + "</td>" +
                    "<td>" + d.fullName + "</td>" +
                    "<td>" + d.address + "</td>" +
                    "<td>" + d.phone + "</td>" +
                "</tr>");
        });
    }

    window.addEventListener('scroll', () => {
        if (window.scrollY + window.innerHeight >= document.documentElement.scrollHeight) {
            console.log('fetching...')
            fetch(API_USERS + '?size=20&page=' + page)
                .then(res => res.json())
                .then(data => {
                    page++;
                    fill(data.content);
                })
                .catch(err => console.log(err))
        }
    });

});

