function loadFile(event) {
          var reader = new FileReader();
          reader.onload = function(){
            var output = document.getElementById('output');
            output.src = reader.result;
            output.style.width = '50px';
            output.style.height = '50px';
          };
          reader.readAsDataURL(event.target.files[0]);
       };