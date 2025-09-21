import React, { useState } from 'react';

function TaskForm({ addTask }) {
  const [title, setTitle] = useState('');
  const handleSubmit = (e) => {
    e.preventDefault();
    if (title.trim() === '') return;
    addTask({ id: Date.now(), title, status: 'pending' });
    setTitle('');
  };
  return (
    <form onSubmit={submit}>
  <label>
    Username
    <input value={username} onChange={e => setUsername(e.target.value)} placeholder="Enter your username" />
  </label>
  <label>
    Password
    <input type="password" value={password} onChange={e => setPassword(e.target.value)} placeholder="Enter your password" />
  </label>
  <button type="submit">Submit</button>
</form>

  );
}

export default TaskForm;
