
document.addEventListener("DOMContentLoaded", function () {
    const moneyInputs = ["value", "minAmount", "maxValue","maxUsage"];
    const hiddenInputs = {};
    const typeSelect = document.getElementById("type");

    moneyInputs.forEach(id => {
        const input = document.getElementById(id);
        if (!input) return;

        // Tạo input ẩn để giữ giá trị thô
        const hidden = document.createElement("input");
        hidden.type = "hidden";
        hidden.name = input.name;
        input.removeAttribute("name"); // bỏ name khỏi input hiển thị
        input.parentNode.insertBefore(hidden, input.nextSibling);
        hiddenInputs[id] = hidden;

        // ✅ Định dạng ban đầu: chỉ format nếu không phải là value hoặc là Fixed
        if (id !== "value" || typeSelect.value === "Fixed") {
            const raw = input.value.replace(/\./g, '');
            input.value = formatNumber(raw);
            hidden.value = raw;
        } else {
            // Nếu type là Percent, không format value
            hidden.value = input.value;
        }

        // Sự kiện khi nhập liệu
        input.addEventListener("input", function () {
            if (id === "value" && typeSelect.value === "Percent") {
                hidden.value = input.value.replace(/[^\d.]/g, '');
            } else {
                const rawValue = input.value.replace(/[^\d]/g, '');
                input.value = formatNumber(rawValue);
                hidden.value = rawValue;
            }
        });

        input.addEventListener("focus", () => {
            setTimeout(() => input.select(), 0);
        });
    });

    // Xử lý khi đổi loại giảm giá
    typeSelect.addEventListener("change", function () {
        const valueInput = document.getElementById("value");
        const hidden = hiddenInputs["value"];

        if (typeSelect.value === "Percent") {
            valueInput.value = hidden.value || valueInput.value.replace(/\./g, '');
        } else {
            const raw = valueInput.value.replace(/[^\d]/g, '');
            valueInput.value = formatNumber(raw);
            hidden.value = raw;
        }
    });

    function formatNumber(value) {
        if (!value) return '';
        return value.replace(/\B(?=(\d{3})+(?!\d))/g, '.');
    }
});

