
document.addEventListener("DOMContentLoaded", function () {
    const moneyInputs = ["value", "minAmount", "maxValue", "maxUsage"];
    const hiddenInputs = {};
    const typeSelect = document.getElementById("type");

    function formatNumber(value) {
        if (!value) return '';
        return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.');
    }

    function getRawDigits(str) {
        if (!str) return '';
        return str.replace(/\D/g, '');
    }

    moneyInputs.forEach(id => {
        const input = document.getElementById(id);
        if (!input) return;

        const hidden = document.createElement("input");
        hidden.type = "hidden";
        hidden.name = input.name;
        input.removeAttribute("name");
        input.parentNode.insertBefore(hidden, input.nextSibling);
        hiddenInputs[id] = hidden;

        const isMoneyFormatted = id !== "value" || typeSelect.value === "Fixed";
        if (isMoneyFormatted) {
            const raw = getRawDigits(input.value);
            input.value = formatNumber(raw);
            hidden.value = raw;
        } else {
            hidden.value = input.value;
        }

        input.addEventListener("input", function (e) {
            const isMoneyFormattedNow = id !== "value" || typeSelect.value === "Fixed";
            
            if (isMoneyFormattedNow) {
                const originalValue = e.target.value;
                const originalCursor = e.target.selectionStart;
                const digitsBeforeCursor = getRawDigits(originalValue.substring(0, originalCursor)).length;

                const rawValue = getRawDigits(originalValue);
                hidden.value = rawValue;

                const formattedValue = formatNumber(rawValue);
                e.target.value = formattedValue;

                let newCursorPos = 0;
                let digitsCounted = 0;
                for (const char of formattedValue) {
                    if (digitsCounted >= digitsBeforeCursor) {
                        break;
                    }
                    newCursorPos++;
                    if (/\d/.test(char)) {
                        digitsCounted++;
                    }
                }
                e.target.setSelectionRange(newCursorPos, newCursorPos);
            } else {
                hidden.value = e.target.value.replace(/[^\d.]/g, '');
            }
        });

        input.addEventListener("focus", () => {
            setTimeout(() => input.select(), 0);
        });
    });

    typeSelect.addEventListener("change", function () {
        const valueInput = document.getElementById("value");
        const hidden = hiddenInputs["value"];

        if (typeSelect.value === "Percent") {
            valueInput.value = hidden.value;
        } else {
            const raw = getRawDigits(valueInput.value);
            valueInput.value = formatNumber(raw);
            hidden.value = raw;
        }
    });
});