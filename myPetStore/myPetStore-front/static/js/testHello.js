
var headline = document.querySelector('.trigger-headline'),
  trigger = document.querySelector('.btn--trigger'),
  segmenter = new Segmenter(document.querySelector('.segmenter'), {
    onReady: function () {
      trigger.classList.remove('btn--hidden')
      trigger.addEventListener('click', function () {
        segmenter.animate()
        headline.classList.remove('trigger-headline--hidden')
        this.classList.add('btn--hidden')
        // 链接待修改
        document.getElementById('enter').innerHTML = "<router-link to='/main/view-main'><button class=\"btn btn--trigger\">Enter the store</button></router-link>"
      })
    }
  })
