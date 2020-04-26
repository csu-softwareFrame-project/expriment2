
function change (isShow) {
  alert('a')
  var password = document.getElementById('password')
  var eyes = document.getElementById('eyes')
  if (isShow) {
    password.type = 'text'
    isShow = false
    eyes.src = '../../../static/images/eye1.png'
  } else {
    password.type = 'password'
    isShow = true
    eyes.src = '../../../static/images/eye2.png'
  }
}

export {
  change
}
