import React, { useState } from 'react';

function TaskList({ tasks, editTask, deleteTask }) {
  const [editingId, setEditingId] = useState(null);
  const [editTitle, setEditTitle] = useState('');
  return (
    <ul>
      {tasks.map(task => (
        <li key={task.id}>
          {editingId === task.id ? (
            <>
              <input value={editTitle} onChange={e => setEditTitle(e.target.value)} />
              <button onClick={() => {
                editTask(task.id, { ...task, title: editTitle });
                setEditingId(null);
              }}>Save</button>
            </>
          ) : (
            <>
              <span>{task.title} [{task.status}]</span>
              <button onClick={() => {
                setEditingId(task.id);
                setEditTitle(task.title);
              }}>Edit</button>
              <button onClick={() => deleteTask(task.id)}>Delete</button>
              {task.status !== 'complete' &&
                <button onClick={() => editTask(task.id, { ...task, status: 'complete' })}>Mark as Complete</button>
              }
              {task.status !== 'pending' &&
                <button onClick={() => editTask(task.id, { ...task, status: 'pending' })}>Mark as Pending</button>
              }
            </>
          )}
        </li>
      ))}
    </ul>
  );
}

export default TaskList;
