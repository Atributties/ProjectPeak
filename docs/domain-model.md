```sql
Table User {
  id int [pk]
  name varchar
  email varchar
  password varchar
  role varchar
}

Table Project {
  id int [pk]
  name varchar
  description varchar
  start_date date
  end_date date
  user_id int [ref: > User.id]
}

Table Task {
  id int [pk]
  name varchar
  description varchar
  start_date date
  end_date date
  status varchar
  project_id int [ref: > Project.id]
}

Table Subtask {
  id int [pk]
  name varchar
  description varchar
  start_date date
  end_date date
  status varchar
  task_id int [ref: > Task.id]
}
    ```
