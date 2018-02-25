/**
 * 
 */
var uploadUtils = {
	
	uploadInit:function(){
		if(!$.support.xhrFormDataFileUpload){
			uploadUtils.loadNewJS("http://www.sub2.com/storage/js/jquery.iframe-transport.js",function(){
				
			});
		}
	},
		
	/**
	 * 上传大文件
	 */
	uploadBigFile:function(inptuFile,fromData){
		uploadUtils.uploadUtilsDone(inptuFile,
				uploadConfig.bigFileUploadUrl,
				uploadConfig.redirectParamName,
				uploadConfig.redirectUrl,
				fromData,
				function(data){
				//done
			    //alert(data.message);
			if(data.status=="success"){
				alert(data.file_paths);
				for(var key in data.file_paths) { 
					console.log(key+"-"+data.file_paths[key]); 
					} 
			}
		     },
		    function(e,data){
			"";//onChangeCallbak "
			 },
			 function(e,data){
			//progressallCallBak
				 var progress = parseInt(data.loaded / data.total * 100, 10);
		            $(".bar").text(progress + '%');
		            $('#progress .bar').css(
		                'width',
		                progress + '%'
		            );
		     },
			function(e,data){
			//failCallbak
			 }
				
		);
	},
		
    /**
	 * 基本上传方法
	 */
	uploadUtilsDone : function(inptuFile, uploadUrl, redirectParamName,
			redirectUrl, fromData, donCallbak, onChangeCallbak,progressallCallBak,failCallbak) {
		inptuFile.fileupload({
			dataType : 'json',
			type : "POST",
			formData : fromData,//lll
			xhrFields : {
				withCredentials : true // cooke
			},
			url : uploadUrl,
			redirectParamName : redirectParamName,
			redirect : redirectUrl,
			done : function(e, data) {
				if (data.result) {
					donCallbak(data.result);
				} else {
					data.fail(e, data);
				}
			},
			progressall : function(e, data) {
				progressallCallBak(e, data);
			},
			fail : function(e, data) {
				failCallbak(e,data);
			}
		});
	},
	loadNewJS : function(url, successCallBak) {
		  var domScript = document.createElement('script');
		  domScript.src = url;
		  successCallBak = successCallBak || function(){};
		  domScript.onload = domScript.onreadystatechange = function() {
		    if (!this.readyState || 'loaded' === this.readyState || 'complete' === this.readyState) {
		    	successCallBak();
		      this.onload = this.onreadystatechange = null;
		    }
		  };
		  document.getElementsByTagName('head')[0].appendChild(domScript);
		  html="/* */   //  \"/";
		  //var   mn =/"/'^\/\//;    
		  var e, t = vt, n = /'|\\/g;
			  r = /\=[\x20\t\r\n\f]*([^'"\]]*)[\x20\t\r\n\f]*\]/g, i = [":focus"];
			 // ddd
		  console.log("*/    //");
	     s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
		}
};