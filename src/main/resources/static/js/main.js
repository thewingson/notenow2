function getIndex(list, id) {
    for (var i=0; i<list.length; i++){
        if(list[i].id == id){
            return i;
        }
    }
    return -1;
}

var todoApi = Vue.resource('/todo{/id}');

Vue.component('todo-form',{
    props:['todos', 'todoAttr'],
    data:function () {
        return{
            text: '',
            id:''
        }
    },
    watch:{
        todoAttr: function(newVal, oldVal){
            this.text = newVal.text;
            this.id = newVal.id;
        }
    },
    template:
        '<div>' +
        '<input type="text" placeholder="Your note..." v-model="text" />' +
        '<input type="button" value="Save" v-on:click="save" />' +
        '</div>',
    methods: {
        save: function () {
            var todo = {text: this.text};
            if(this.id){
                todoApi.update({id:this.id}, todo).then(result =>
                    result.json().then(data =>{
                        var index = getIndex(this.todos, data.id);
                        this.todos.splice(index, 1, data);
                        this.text = ''
                        this.id = ''
                    })
                )
            }else {
                    todoApi.save({}, todo).then(result =>
                        result.json().then(data => {
                            this.todos.push(data);
                            this.text = ''
                        })
                    )
            }
        }
    }
});

Vue.component('todo-row', {
    props: ['todo', 'editMethod', 'todos'],
    template:
        '<div class="col-lg-6">' +
            '<span style="position: relative; left: 0">' +
                '<i>{{todo.id}}</i> {{todo.text}}  <strong>{{todo.authorName}}</strong>' +
            '</span>' +
            '<span style="position: absolute; right: 0;" >' +
                '<input type="button" value="Edit" v-on:click="edit" />' +
                '<input type="button" value="X" v-on:click="del" />' +
            '</span>' +
        '</div>',
    methods:{
        edit: function () {
            this.editMethod(this.todo);
        },
        del: function () {
            todoApi.remove({id: this.todo.id}).then(result =>{
                if(result.ok){
                    this.todos.splice(this.todos.indexOf(this.todo), 1)
                }
            })
        }
    }
});

Vue.component('todo-list', {
    props: ['todos'],
    data: function(){
        return{
            todo:null
        }
    },
    template:
        '<div  >' + //style="position: relative; width: 300px;"
            '<todo-form :todos="todos" :todoAttr="todo"/>' +
            '<todo-row v-for="todo in todos" :key="todo.id" :todo="todo" ' +
            ':editMethod="editMethod" :todos="todos"/>' +
        '</div>',

    methods:{
        editMethod: function (todo) {
            this.todo = todo;
        }
        
    }

});

var app = new Vue({
    el: '#app',
    template:
    '<div class="container">' +
        '<div class="row">' +
            '<div class="col-lg-12" align="center">' +
                '<div v-if="!profile">Login through the <a href="/login">Google</a></div>' +
                '<div v-else>' +
                    '<div>{{profile.name}}&nbsp;<a href="/logout">Logout</a></div>' +
                    '<todo-list :todos="todos"/>' +
                '</div>' +
            '</div>' +
        '</div>' +
    '</div>',
    data: {
        todos: frontendData.todos,
        profile: frontendData.profile
    },
    created: function(){
        /*todoApi.get().then(result =>
            result.json().then(data =>
            data.forEach(todo => this.todos.push(todo))
            )
        )*/
    },
});