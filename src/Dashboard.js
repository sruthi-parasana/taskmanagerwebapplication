import React, { useState, useEffect } from 'react';
import TaskForm from './TaskForm';
import TaskList from './TaskList';

function Dashboard({ userEmail, onLogout }) {
  const storageKey = 'tasks_' + userEmail;
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    const savedTasks = JSON.parse(localStorage.getItem(storageKey) || '[]');
    setTasks(savedTasks);
  }, [storageKey]);

  const updateStorage = (tasks) => {
    setTasks(tasks);
    localStorage.setItem(storageKey, JSON.stringify(tasks));
  };

  const addTask = (task) => {
    updateStorage([...tasks, task]);
  };

  const editTask = (id, updatedTask) => {
    updateStorage(tasks.map(t => t.id === id ? updatedTask : t));
  };

  const deleteTask = (id) => {
    updateStorage(tasks.filter(t => t.id !== id));
  };

  return (
    <div>
      <button onClick={onLogout}>Logout</button>
      <h2>Task Dashboard</h2>
      <TaskForm addTask={addTask} />
      <TaskList tasks={tasks} editTask={editTask} deleteTask={deleteTask} />
    </div>
  );
}

export default Dashboard;
