if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        fetch('/url', {
            method: 'DELETE'
        })
            .then(() => {
                alert('삭제 완료');
                location.replace('/articles');
            })
    })
}