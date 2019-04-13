<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<div>
    <@l.logout/>
</div>
<div>
   <form method="post" enctype="multipart/form-data">
       <input type="file" name="image" placeholder="Insert your image"/>
       <input type="hidden" name="_csrf" value="${_csrf.token}"/>
       <button type="submit">Add</button>
   </form>
</div>

<div>List of images</div>
<#list images as image>
<div>
    <strong>${image.author.username}</strong>
    <div>
        <#if image.url??>
            <img src="/img/${image.url}"/>
        </#if>
    </div>
</div>
<#else>
<div>No images</div>
</#list>

</@c.page>
