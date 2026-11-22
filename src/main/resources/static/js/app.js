document.querySelectorAll('[data-confirm-delete]').forEach((form) => {
  form.addEventListener('submit', (event) => {
    if (!window.confirm('Удалить запись студента? Это действие нельзя отменить.')) {
      event.preventDefault();
    }
  });
});
