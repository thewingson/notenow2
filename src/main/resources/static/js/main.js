var todoApi = Vue.resource('/todo{/id}');

Vue.component('todo-row', {
    props: ["todo"],
    template:
        '<div>' +
            '<i>{{todo.id}}</i>' +
            ' {{todo.text}}' +
        '</div>'
});

Vue.component('todo-list', {
    props: ["todos"],
    template:
        '<div>' +
            '<todo-row v-for="todo in todos" :key="todo.id" :todo="todo" />' +
        '</div>',
    created: function(){
        todoApi.get().then(result =>
            result.json().then(data =>
                data.forEach(todo => this.todos.push(todo))
            )
        )
    }
});

var app = new Vue({
    el: '#app',
    template: '<todo-list :todos="todos" />',
    data: {
        todos: []
    }
});