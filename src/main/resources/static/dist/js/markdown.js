document.addEventListener("DOMContentLoaded", function() {
    const converter = new showdown.Converter();

    function processContent(contentElement) {
        const markdownText = contentElement.textContent.trim();
        let htmlContent = converter.makeHtml(markdownText);
        htmlContent = removeMarkdownSyntax(htmlContent);
        contentElement.innerHTML = htmlContent;
    }

    function removeMarkdownSyntax(text) {
        // 제목 제거 (예: ### Title)
        text = text.replace(/#+\s/g, '');
        // 강조 표시 제거 (예: **bold**, *italic*)
        text = text.replace(/(\*{1,2}|_{1,2})/g, '');
        // 링크 제거 (예: [Link](url))
        text = text.replace(/\[(.*?)\]\(.*?\)/g, '$1');
        // 이미지 제거 (예: ![Alt text](url))
        text = text.replace(/!\[.*?\]\(.*?\)/g, '');
        text = text.replace(/<img[^>]*>/g, '');
        // 수평선 제거 (예: ---)
        text = text.replace(/-{3,}/g, '');
        // 코드 블록 제거 (예: ``` code ```)
        text = text.replace(/```.*?```/g, '');
        return text;
    }

    document.querySelectorAll('.commContent, .commDeContent, .commNoDeContent, .adcontent').forEach(processContent);
});
